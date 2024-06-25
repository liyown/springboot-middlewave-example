package com.lyw.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @TableName tb_shop
 */
@TableName(value ="tb_shop")
@Data
public class Shop implements Serializable {
    private Long id;

    private String name;

    private Long typeId;

    private String images;

    private String area;

    private String address;

    private Double x;

    private Double y;

    private Long avgPrice;

    private Integer sold;

    private Integer comments;

    private Integer score;

    private String openHours;

    private Date createTime;

    private Date updateTime;

    @TableField(exist = false)
    private Double distance;

    private static final long serialVersionUID = 1L;
}