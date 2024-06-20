package com.lyw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName tb_seckill_voucher
 */
@TableName(value ="tb_seckill_voucher")
@Data
public class SeckillVoucher implements Serializable {

    @TableId(value = "id", type = IdType.AUTO)
    private Long voucherId;

    private Integer stock;

    private Date createTime;

    private Date beginTime;

    private Date endTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}