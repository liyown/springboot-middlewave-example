package com.lyw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName tb_follow
 */
@TableName(value ="tb_follow")
@Data
public class Follow implements Serializable {
    private Long id;

    private Long userId;

    private Long followUserId;

    private Date createTime;

    private static final long serialVersionUID = 1L;
}