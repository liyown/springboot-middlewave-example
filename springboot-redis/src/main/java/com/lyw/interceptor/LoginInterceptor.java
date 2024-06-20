package com.lyw.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyw.dto.UserDTO;
import com.lyw.pojo.User;
import com.lyw.utils.JsonUtils;
import com.lyw.utils.UserHolder;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.ibatis.jdbc.Null;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import static com.lyw.utils.RedisConstants.LOGIN_USER_KEY;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-19 11:02
 * @Description:
 */
public class LoginInterceptor implements HandlerInterceptor
{

    private final StringRedisTemplate stringRedisTemplate;

    public LoginInterceptor(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;

    }

    /**
     * 拦截器校验用户
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param handler  Object
     * @return boolean
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
    {
        // 获取token
        String token = request.getHeader("authorization");
        String userDTO = stringRedisTemplate.opsForValue().get(LOGIN_USER_KEY+token);

        // 判断用户是否登录
        if (userDTO == null) {
            // 未登录
            response.setStatus(401);
            return false;
        }
        // 已登录
        UserHolder.saveUser(JsonUtils.toObject(userDTO,UserDTO.class).orElse(new UserDTO()));
        return true;
    }
}