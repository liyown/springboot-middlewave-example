package com.lyw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.lyw.dto.Result;
import com.lyw.pojo.ShopType;
import com.lyw.service.ShopTypeService;
import com.lyw.mapper.ShopTypeMapper;
import com.lyw.utils.JsonUtils;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

import static com.lyw.utils.RedisConstants.SHOP_TYPE_KEY;

/**
* @author liuya
* @description 针对表【tb_shop_type】的数据库操作Service实现
* @createDate 2024-06-19 09:22:40
*/
@Service
public class ShopTypeServiceImpl extends ServiceImpl<ShopTypeMapper, ShopType>
    implements ShopTypeService{

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public Result queryTypeList() {

        // 从redis中获取数据
        String typeListJson = stringRedisTemplate.opsForValue().get(SHOP_TYPE_KEY);
        if (typeListJson != null) {
            List<ShopType> typeList = JsonUtils.toObject(typeListJson, new TypeReference<List<ShopType>>() {}).orElseThrow();
            return Result.ok(typeList);
        }

        // 从数据库中获取数据
        List<ShopType> typeList =
                this.query().orderByAsc("sort").list();

        // 将数据存入redis
        stringRedisTemplate.opsForValue().set(SHOP_TYPE_KEY, JsonUtils.toJson(typeList).orElseThrow());


        return Result.ok(typeList);
    }
}




