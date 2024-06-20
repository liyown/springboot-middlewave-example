package com.lyw.config;

import com.lyw.interceptor.LoginInterceptor;
import com.lyw.interceptor.RefreshTokenInterceptor;
import jakarta.annotation.Resource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-19 11:08
 * @Description:
 */
@Component
public class WebMVCConfiguration implements WebMvcConfigurer {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //添加拦截器，登录拦截器
        registry.addInterceptor(new LoginInterceptor(stringRedisTemplate)).excludePathPatterns(
                "/shop/**",
                "/voucher/**",
                "/shop-type/**",
                "/upload/**",
                "/blog/hot",
                "/user/code",
                "/user/login"
        ).order(1);

        registry.addInterceptor(new RefreshTokenInterceptor(stringRedisTemplate)).excludePathPatterns(
                "/user/code",
                "/user/login").order(0);
    }
}
