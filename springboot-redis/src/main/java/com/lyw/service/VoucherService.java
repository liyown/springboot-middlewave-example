package com.lyw.service;

import com.lyw.pojo.Voucher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
* @author liuya
* @description 针对表【tb_voucher】的数据库操作Service
* @createDate 2024-06-19 09:22:40
*/
public interface VoucherService extends IService<Voucher> {

    List<Voucher> queryVoucherOfShop(Long shopId);

    boolean addVoucher(Voucher voucher);
}
