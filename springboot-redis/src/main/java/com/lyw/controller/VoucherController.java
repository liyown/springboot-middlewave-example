package com.lyw.controller;

import com.lyw.dto.Result;
import com.lyw.pojo.SeckillVoucher;
import com.lyw.pojo.Voucher;
import com.lyw.service.SeckillVoucherService;
import com.lyw.service.VoucherService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-20 18:50
 * @Description:
 */
@RestController
@RequestMapping("/voucher")
public class VoucherController {

    @Resource
    private VoucherService voucherService;

    @Resource
    private SeckillVoucherService seckillVoucherService;

    /**
     * 新增普通券
     * @param voucher 优惠券信息
     * @return 优惠券id
     */
    @PostMapping
    public Result addVoucher(@RequestBody Voucher voucher) {
        System.out.println(voucher);
        boolean b = voucherService.addVoucher(voucher);
        Optional<Result> result = Optional.of(b).map(value -> value ? Result.ok(voucher.getId()) : Result.fail("添加失败"));
        return result.orElse(Result.fail("添加失败"));
    }

    /**
     * 新增秒杀券
     * @param voucher 优惠券信息，包含秒杀信息
     * @return 优惠券id
     */
    @PostMapping("seckill")
    public Result addSeckillVoucher(@RequestBody SeckillVoucher voucher) {
        boolean b = seckillVoucherService.addSeckillVoucher(voucher);
        Optional<Result> result = Optional.of(b).map(value -> value ? Result.ok(voucher.getVoucherId()) : Result.fail("添加失败"));
        return result.orElse(Result.fail("添加失败"));
    }

    /**
     * 查询店铺的优惠券列表
     * @param shopId 店铺id
     * @return 优惠券列表
     */
    @GetMapping("/list/{shopId}")
    public Result queryVoucherOfShop(@PathVariable("shopId") Long shopId) {
        return Result.ok(voucherService.queryVoucherOfShop(shopId));
    }
}