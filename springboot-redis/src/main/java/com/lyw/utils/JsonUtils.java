package com.lyw.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.jayway.jsonpath.TypeRef;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

/**
 * @author: liuyaowen
 * @poject: UserController.java
 * @create: 2024-06-19 15:27
 * @Description:
 */
@Slf4j
public class JsonUtils {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }

    public static Optional<String> toJson(Object target) {
        try {
            return Optional.ofNullable(objectMapper.writeValueAsString(target));
        } catch (JsonProcessingException e) {
            log.warn("target {} is failed to JSON",target);
        }
        return Optional.empty();
    }


    public static <T> Optional<T> toObject(String target, Class<T> clazz) {
        try {
            return Optional.ofNullable(objectMapper.readValue(target, clazz));
        } catch (JsonProcessingException e) {
            log.warn("target {} is failed to {} Object",target,clazz.toString());
        }
        return Optional.empty();
    }

    public static <T> Optional<T> toObject(String target, TypeReference<T> clazz) {
        try {
            return Optional.ofNullable(objectMapper.readValue(target, clazz));
        } catch (JsonProcessingException e) {
            log.warn("target {} is failed to {} Object",target,clazz.toString());
        }
        return Optional.empty();
    }

}
