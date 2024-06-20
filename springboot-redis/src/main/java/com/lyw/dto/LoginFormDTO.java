package com.lyw.dto;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-19 11:23
 * @Description:
 */
import lombok.Data;

@Data
public class LoginFormDTO
{
    private String phone;
    private String code;
    private String password;
}
