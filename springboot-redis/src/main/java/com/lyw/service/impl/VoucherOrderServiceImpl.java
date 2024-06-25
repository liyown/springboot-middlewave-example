package com.lyw.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyw.dto.Result;
import com.lyw.dto.UserDTO;
import com.lyw.pojo.SeckillVoucher;
import com.lyw.pojo.VoucherOrder;
import com.lyw.service.SeckillVoucherService;
import com.lyw.service.VoucherOrderService;
import com.lyw.mapper.VoucherOrderMapper;
import com.lyw.utils.RedisIDGenerator;
import com.lyw.utils.UserHolder;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import net.sf.jsqlparser.statement.Block;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.aop.framework.AopContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.stream.*;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* @author liuya
* @description 针对表【tb_voucher_order】的数据库操作Service实现
* @createDate 2024-06-19 09:22:40
*/
//暴露代理对象，解决事务失效问题
@Service
@Slf4j
public class VoucherOrderServiceImpl extends ServiceImpl<VoucherOrderMapper, VoucherOrder>
    implements VoucherOrderService{

    @Resource
    private RedissonClient redissonClient;

    @Resource
    private RedisIDGenerator redisIDGenerator;
    @Resource
    private SeckillVoucherService seckillVoucherService;

    @Resource
    private  StringRedisTemplate stringRedisTemplate;

    static DefaultRedisScript<Long> longDefaultRedisScript;
    static {
        longDefaultRedisScript = new DefaultRedisScript<>();
        longDefaultRedisScript.setLocation(new ClassPathResource("seckill.lua"));
        longDefaultRedisScript.setResultType(Long.class);
    }
    private static final ExecutorService executorService = Executors.newFixedThreadPool(2);

    // 定义一个信号量
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    @PostConstruct
    private void init() {
//        executorService.execute(new SeckillTask());
//        executorService.execute(new seckillPandingTask());
    }
    private class SeckillTask implements Runnable {
        @Override
        public void run() {
            while(true) {
                Long OrderId = null;
                lock.lock();
                try {
                    List<MapRecord<String, String,String>> read = stringRedisTemplate.<String,String>opsForStream().read(
                            Consumer.from("seckillGroup", "c1"),
                            StreamReadOptions.empty().count(1).block(Duration.ofSeconds(1)),
                            StreamOffset.create("stream.order", ReadOffset.lastConsumed()));
                    if (read == null || read.isEmpty()) {
                        log.info("没有订单数据, 等待中...");
                        continue;
                    }

                    Map<String,String> value = read.get(0).getValue();

                    OrderId = Long.parseLong(value.get("id"));

                    VoucherOrder voucherOrder = BeanUtil.fillBeanWithMap(value, new VoucherOrder(), true);

                    headerSeckillOrder(voucherOrder);

                    stringRedisTemplate.<String,String>opsForStream().acknowledge("stream.order", "seckillGroup", read.get(0).getId());
                    log.info("处理订单, 订单号：{}", OrderId);
                } catch (Exception e) {
                    log.info("秒杀订单处理异常, 订单号：{}", OrderId, e);
                    condition.signal();
                } finally {
                    lock.unlock();
                }
            }
        }
    }

    private class seckillPandingTask implements Runnable {
        @Override
        public void run() {
            while(true) {
                Long OrderId = null;
                lock.lock();
                try {
                    List<MapRecord<String, String,String>> read = stringRedisTemplate.<String,String>opsForStream().read(
                            Consumer.from("seckillGroup", "c1"),
                            StreamReadOptions.empty().count(1).block(Duration.ofSeconds(1)),
                            StreamOffset.create("stream.order", ReadOffset.from("0")));
                    if (read == null || read.isEmpty()) {
                        log.info("没有异常订单数据, 等待中...");

                        condition.await();
                        continue;
                    }

                    Map<String,String> value = read.get(0).getValue();

                    OrderId = Long.parseLong(value.get("id"));

                    VoucherOrder voucherOrder = BeanUtil.fillBeanWithMap(value, new VoucherOrder(), true);

                    headerSeckillOrder(voucherOrder);

                    stringRedisTemplate.<String,String>opsForStream().acknowledge("stream.order", "seckillGroup", read.get(0).getId());

                    log.info("处理异常订单, 订单号：{}", OrderId);
                } catch (Exception e) {
                    log.info("异常订单处理异常, 订单号：{}", OrderId, e);
                } finally {
                    lock.unlock();

                }
            }
        }
    }

    private void headerSeckillOrder(VoucherOrder voucherOrder) {
        this.save(voucherOrder);
    }

    @Override
    public Result seckillVoucher(Long voucherId) throws InterruptedException {
        // 执行脚本
        Long userID = Optional.of(UserHolder.getUser()).map(UserDTO::getId).orElse(null);
        if (userID == null) {
            return Result.fail("秒杀失败,请先登录");
        }

        Long OrderId = redisIDGenerator.nextID("voucherOrder");

        Long execute = stringRedisTemplate.execute(longDefaultRedisScript, Collections.emptyList(), voucherId.toString(), userID.toString(), OrderId.toString());

        if (execute == null) return Result.fail("秒杀失败");

        if (execute == 0) return Result.fail("秒杀失败");

        return Result.ok(OrderId);

    }

    private Result getResult(Long voucherId) throws InterruptedException {
        SeckillVoucher seckillVoucher = seckillVoucherService.getById(voucherId);
        // 1. 判断秒杀时间是否为空，如果为空则返回秒杀失败
        LocalDateTime beginTime = Optional.ofNullable(seckillVoucher).map(SeckillVoucher::getBeginTime).orElse(null);
        LocalDateTime endTime = Optional.ofNullable(seckillVoucher).map(SeckillVoucher::getEndTime).orElse(null);
        if (beginTime == null || endTime == null) {
            return Result.fail("秒杀失败");
        }
        // 2. 判断当前时间是否在秒杀时间范围内，如果不在则返回秒杀失败
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(beginTime) || now.isAfter(endTime)) {
            return Result.fail("秒杀失败");
        }
        String lockName = "seckill:" + UserHolder.getUser().getId() + ":" + voucherId;
        RLock lock = redissonClient.getLock(lockName);
        boolean b = lock.tryLock(1, 30, TimeUnit.SECONDS);
        if (!b) {
            return Result.fail("秒杀失败");
        }
        try {
            return ((VoucherOrderService) AopContext.currentProxy()).getResult(voucherId, seckillVoucher);
        } finally {
            lock.unlock();
        }
    }

    @Transactional
    public Result getResult(Long voucherId, SeckillVoucher seckillVoucher) {
        boolean exist = this.lambdaQuery().eq(VoucherOrder::getVoucherId, voucherId).eq(VoucherOrder::getUserId, UserHolder.getUser().getId()).exists();
        if (exist) {
            return Result.fail("秒杀失败");
        }

        // 3. 判断库存是否大于0，如果不大于0则返回秒杀失败
        int stock = Optional.of(seckillVoucher).map(SeckillVoucher::getStock).orElse(0);
        if (stock <= 0) {
            return Result.fail("秒杀失败");
        }
        // 4. 扣减库存
        boolean update = seckillVoucherService.update().setSql("stock = stock - 1").eq("voucher_id", voucherId).eq("stock", stock).update();
        if (!update) {
            return Result.fail("秒杀失败");
        }

        // 5. 生成订单
        VoucherOrder voucherOrder = new VoucherOrder();
        Long id = redisIDGenerator.nextID("voucher_order");
        voucherOrder.setId(id);
        voucherOrder.setVoucherId(voucherId);
        voucherOrder.setUserId(UserHolder.getUser().getId());
        voucherOrder.setCreateTime(LocalDateTime.now());
        this.save(voucherOrder);

        return Result.ok(voucherOrder.getId());
    }
}




