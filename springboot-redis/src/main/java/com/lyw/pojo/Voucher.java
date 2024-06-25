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
 * @TableName tb_voucher
 */
@TableName(value ="tb_voucher")
@Data
public class Voucher implements Serializable {
    private Long id;

    private Long shopId;

    private String title;

    private String subTitle;

    private String rules;

    private Long payValue;

    private Long actualValue;

    private Integer type;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    @TableField(exist = false)
    private SeckillVoucher seckillVoucher;

    @TableField(exist = false)
    private Integer stock;

    @TableField(exist = false)
    private LocalDateTime beginTime;

    @TableField(exist = false)
    private LocalDateTime endTime;


    private static final long serialVersionUID = 1L;
}