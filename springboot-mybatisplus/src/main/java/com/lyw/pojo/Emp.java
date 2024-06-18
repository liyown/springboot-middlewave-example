package com.lyw.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author: liuyaowen
 * @poject: springboot
 * @create: 2024-06-05 18:07
 * @Description:
 */
@Data
@TableName("t_emp")
public class Emp {
    // 数据库id为emp_id
    @TableId("emp_id")
    private Integer empId;

    private String empName;
    private String empSalary;
}
