package com.lyw.controller;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-20 20:37
 * @Description:
 */

import com.lyw.dto.Result;
import com.lyw.service.SeckillVoucherService;
import com.lyw.service.VoucherOrderService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 虎哥
 * @since 2021-12-22
 */
@RestController
@RequestMapping("/voucher-order")
public class VoucherOrderController {

    @Resource
    private VoucherOrderService voucherOrderService;



    @PostMapping("seckill/{id}")
    public Result seckillVoucher(@PathVariable("id") Long voucherId) throws InterruptedException {
        return voucherOrderService.seckillVoucher(voucherId);
    }
}