package com.lyw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lyw.pojo.SeckillVoucher;
import com.lyw.service.SeckillVoucherService;
import com.lyw.mapper.SeckillVoucherMapper;
import org.springframework.stereotype.Service;

/**
* @author liuya
* @description 针对表【tb_seckill_voucher(秒杀优惠券表，与优惠券是一对一关系)】的数据库操作Service实现
* @createDate 2024-06-19 09:22:40
*/
@Service
public class SeckillVoucherServiceImpl extends ServiceImpl<SeckillVoucherMapper, SeckillVoucher>
    implements SeckillVoucherService{

}




