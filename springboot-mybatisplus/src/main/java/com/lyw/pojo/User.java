package com.lyw.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@TableName("user") // 指定数据库表名
public class User {
    @TableId(value = "id", type = IdType.AUTO) // 指定主键列名
    private Long id;

    @TableField("name") // 指定列名
    private String name;

    @TableField("age") // 指定列名
    private Integer age;

    @Version
    private Integer version;
}
