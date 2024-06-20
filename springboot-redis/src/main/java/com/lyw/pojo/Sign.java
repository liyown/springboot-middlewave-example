package com.lyw.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * @TableName tb_sign
 */
@TableName(value ="tb_sign")
@Data
public class Sign implements Serializable {
    private Long id;

    private Long userId;

    private Object year;

    private Integer month;

    private Date date;

    private Integer isBackup;

    private static final long serialVersionUID = 1L;
}