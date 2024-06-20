package com.lyw.utils;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;

@Data
public class RedisData<T>{
    private Instant expireTime;
    private T data;
}
