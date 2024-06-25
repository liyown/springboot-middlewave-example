package com.lyw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyw.pojo.Voucher;
import com.lyw.service.VoucherService;
import com.lyw.mapper.VoucherMapper;
import org.springframework.stereotype.Service;

import java.util.List;

/**
* @author liuya
* @description 针对表【tb_voucher】的数据库操作Service实现
* @createDate 2024-06-19 09:22:40
*/
@Service
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher>
    implements VoucherService{

    @Override
    public List<Voucher> queryVoucherOfShop(Long shopId) {
        return getBaseMapper().queryVoucherOfShop(shopId);
    }

    @Override
    public boolean addVoucher(Voucher voucher) {
        return this.save(voucher);
    }
}




