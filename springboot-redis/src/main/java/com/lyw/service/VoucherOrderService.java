package com.lyw.service;

import com.lyw.dto.Result;
import com.lyw.pojo.SeckillVoucher;
import com.lyw.pojo.VoucherOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author liuya
* @description 针对表【tb_voucher_order】的数据库操作Service
* @createDate 2024-06-19 09:22:40
*/
public interface VoucherOrderService extends IService<VoucherOrder> {

   Result seckillVoucher(Long voucherId) throws InterruptedException;

   Result getResult(Long voucherId, SeckillVoucher seckillVoucher);
}
