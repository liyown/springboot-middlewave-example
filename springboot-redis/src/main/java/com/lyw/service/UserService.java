package com.lyw.service;

import com.lyw.dto.LoginFormDTO;
import com.lyw.dto.Result;
import com.lyw.pojo.User;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
* @author liuya
* @description 针对表【tb_user】的数据库操作Service
* @createDate 2024-06-19 09:22:40
*/
public interface UserService extends IService<User> {

    Result sendCode(String phone, HttpSession session);

    Result login(LoginFormDTO loginForm, HttpSession session);

    Result sign();

    Result signCount();

    Result logout(HttpServletRequest request, HttpServletResponse response);
}
