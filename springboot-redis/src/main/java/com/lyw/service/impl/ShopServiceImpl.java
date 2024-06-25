package com.lyw.service.impl;

import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lyw.dto.Result;
import com.lyw.mapper.ShopMapper;
import com.lyw.pojo.Shop;
import com.lyw.service.ShopService;
import com.lyw.utils.JsonUtils;
import com.lyw.utils.RedisData;
import com.lyw.utils.SystemConstants;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.domain.geo.GeoReference;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static com.lyw.utils.RedisConstants.*;

/**
* @author liuya
* @description 针对表【tb_shop】的数据库操作Service实现
* @createDate 2024-06-19 09:22:40
*/
@Slf4j
@Service
public class ShopServiceImpl extends ServiceImpl<ShopMapper, Shop>
    implements ShopService{

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryShopByType(Integer typeId, Integer current, Double x, Double y) {
        loadGEOByShopType(Long.valueOf(typeId));
        // 计算分页参数
        int start = (current - 1) * SystemConstants.DEFAULT_PAGE_SIZE;
        int end = start + SystemConstants.DEFAULT_PAGE_SIZE;

        // 按照距离排序
        GeoResults<RedisGeoCommands.GeoLocation<String>> search = stringRedisTemplate.opsForGeo().search(
                SHOP_GEO_KEY + typeId,
                GeoReference.fromCoordinate(x, y),
                new Distance(5, Metrics.KILOMETERS),
                RedisGeoCommands.GeoRadiusCommandArgs.newGeoRadiusArgs()
                        .sortAscending()
                        .includeDistance()
                        .limit(end)
        );
        if (search == null || search.getContent().isEmpty()) {
            return Result.fail("商铺不存在");
        }


        // 手动分页
        List<Shop> shopList = search.getContent().stream()
                .skip(start)
                .map(geoLocation -> {
                    String shopId = geoLocation.getContent().getName();
                    // 获取距离
                    Distance distance = geoLocation.getDistance();
                    Long id = Long.valueOf(shopId);
                    Shop shop = queryByIdWithExpire(id);
                    System.out.println("----------------{}" + shop.toString());
                    shop.setDistance(distance.getValue());
                    return shop;
                })
                .toList();

        return Result.ok(shopList);
    }

    @Override
    public Result queryById(Long id) {
        Shop shop = queryByIdWithExpire(id);
        if (shop == null) {
            return Result.fail("商铺不存在");
        }
        return Result.ok(shop);
    }
    private boolean tryLock(String key) {
        Boolean b = stringRedisTemplate.opsForValue().setIfAbsent(key, "1", 10, TimeUnit.SECONDS);
        return BooleanUtil.isTrue(b);
    }

    private void unlock(String key) {
        stringRedisTemplate.delete(key);
    }

    private Shop queryByIdWithLock(Long id) {

        while (true) {
            String s = stringRedisTemplate.opsForValue().get(CACHE_SHOP_KEY + id);

            // 排除为 null且不为 "" 的情况
            if (Optional.ofNullable(s).map(value -> !value.isEmpty()).orElse(false)) {
                return JsonUtils.toObject(s, Shop.class).orElseThrow();
            }
            // 排除为 "" 的情况
            if (Optional.ofNullable(s).isPresent()) {
                return null;
            }

            // 获取锁
            String key = "lock:shop:" + id;
            boolean lock = tryLock(key);
            if (lock) {
                log.debug("获取到锁");
                try {
                    Shop shop = lambdaQuery().eq(Shop::getId, id).one();
                    if (shop != null) {
                        stringRedisTemplate.opsForValue().set(CACHE_SHOP_KEY + id, JsonUtils.toJson(shop).orElse(""));
                        return shop;
                    } else {
                        stringRedisTemplate.opsForValue().set(CACHE_SHOP_KEY + id, "", 5, TimeUnit.MINUTES);
                        return null;
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                } finally {
                    unlock(key);
                }
            }
            log.debug("循环重试");
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private Shop queryByIdWithExpire(Long id) {
        String s = stringRedisTemplate.opsForValue().get(CACHE_SHOP_KEY + id);
        if (s == null || s.isEmpty()) {
            return savaShopWithExpire(id);
        }

        RedisData<Shop> redisData = JsonUtils.toObject(s, new TypeReference<RedisData<Shop>>() {
        }).orElseThrow();

        if (Instant.now().isAfter(redisData.getExpireTime()) && tryLock(LOCK_SHOP_KEY + id)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            savaShopWithExpire(id);
                        } finally {
                            unlock(LOCK_SHOP_KEY + id);
                        }
                    }
                }).start();
        }
        return redisData.getData();
    }
    private Shop savaShopWithExpire(Long id) {
        Shop shop = lambdaQuery().eq(Shop::getId, id).one();
        if (shop != null) {
            RedisData<Shop> redisData = new RedisData<>();
            redisData.setExpireTime(Instant.now().plusSeconds(CACHE_EXPIRE_TTL));
            redisData.setData(shop);
            stringRedisTemplate.opsForValue().set(CACHE_SHOP_KEY + shop.getId(), JsonUtils.toJson(redisData).orElseThrow());
        } else {
            stringRedisTemplate.opsForValue().set(CACHE_SHOP_KEY + id, "", 5, TimeUnit.MINUTES);
        }
        return shop;
    }

    @Override
    public Result saveShop(Shop shop) {
        this.save(shop);
        // 删除缓存
        stringRedisTemplate.delete(CACHE_SHOP_KEY + shop.getId());

        return Result.ok(shop.getId());
    }

    @Override
    @Transactional
    public Result updateShopId(Shop shop) {
        Shop one = this.lambdaQuery().eq(Shop::getId, shop.getId()).one();
        if (one == null) {
            return Result.fail("商铺不存在");
        }

        this.updateById(shop);
        // 删除缓存
        stringRedisTemplate.delete(CACHE_SHOP_KEY + shop.getId());

        return Result.ok();
    }

    public void loadGEOByShopType(Long TypeId) {
        // 判断是否存在
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(SHOP_GEO_KEY + TypeId))) {
            return;
        }
        // 根据类型分页查询
        query().eq("type_id", TypeId).list().forEach(shop -> {
            // 写入GEO
            Point point = new Point(shop.getX(), shop.getY());
            stringRedisTemplate.opsForGeo().add(SHOP_GEO_KEY + TypeId, point, shop.getId().toString());
        });

        // 设置过期时间
        stringRedisTemplate.expire(SHOP_GEO_KEY + TypeId, SHOP_GEO_TTL, TimeUnit.MINUTES);
    }
}




