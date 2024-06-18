package com.lyw.aop;

import jakarta.websocket.Endpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author: liuyaowen
 * @poject: springboot
 * @create: 2024-06-13 15:19
 * @Description:
 */
@Aspect
@Component
public class ControllerAdvise {


    @Before("execution(* com.lyw.controller.*.*(..))")
    public void pointcut(JoinPoint joinPoint) {
        System.out.println("before");
    }
}
