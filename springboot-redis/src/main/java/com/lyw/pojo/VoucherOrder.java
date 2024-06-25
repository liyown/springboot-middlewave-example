package com.lyw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import lombok.Data;

/**
 * @TableName tb_voucher_order
 */
@TableName(value ="tb_voucher_order")
@Data
public class VoucherOrder implements Serializable {
    private Long id;

    private Long userId;

    private Long voucherId;

    private Integer payType;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime payTime;

    private LocalDateTime useTime;

    private LocalDateTime refundTime;

    private LocalDateTime updateTime;

    private static final long serialVersionUID = 1L;
}