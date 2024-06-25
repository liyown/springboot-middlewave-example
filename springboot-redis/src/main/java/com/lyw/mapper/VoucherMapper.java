package com.lyw.mapper;

import com.lyw.pojo.Voucher;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
* @author liuya
* @description 针对表【tb_voucher】的数据库操作Mapper
* @createDate 2024-06-19 09:22:40
* @Entity com.lyw.pojo.Voucher
*/
public interface VoucherMapper extends BaseMapper<Voucher> {

    public List<Voucher> queryVoucherOfShop(Long shopId);

}




