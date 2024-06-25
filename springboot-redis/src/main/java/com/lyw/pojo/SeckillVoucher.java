package com.lyw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * @TableName tb_seckill_voucher
 */
@TableName(value ="tb_seckill_voucher")
@Data
public class SeckillVoucher implements Serializable {

    @TableId(value = "voucher_id", type = IdType.AUTO)
    private Long voucherId;

    private Integer stock;

    private LocalDateTime createTime;

    private LocalDateTime beginTime;

    private LocalDateTime endTime;

    private LocalDateTime updateTime;

    @Serial
    private static final long serialVersionUID = 1L;
}