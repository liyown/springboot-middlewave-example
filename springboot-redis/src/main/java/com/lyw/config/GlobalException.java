package com.lyw.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-19 09:33
 * @Description:
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(value = RuntimeException.class)
    public String exceptionHandler(Exception e) {
        // 打印异常信息
        log.error(e.getMessage(), e);
        return e.toString();
    }
}
