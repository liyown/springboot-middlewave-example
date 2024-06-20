package com.lyw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName tb_blog
 */
@TableName(value ="tb_blog")
@Data
public class Blog implements Serializable {

    @TableField(exist = false)
    private String icon;
    /**
     * 用户姓名
     */
    @TableField(exist = false)
    private String name;
    /**
     * 是否点赞过了
     */
    @TableField(exist = false)
    private Boolean isLike;


    private Long id;

    private Long shopId;

    private Long userId;

    private String title;

    private String images;

    private String content;

    private Integer liked;

    private Integer comments;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;
}