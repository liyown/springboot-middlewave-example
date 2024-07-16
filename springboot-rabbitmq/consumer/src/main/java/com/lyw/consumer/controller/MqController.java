package com.lyw.consumer.controller;

import jakarta.annotation.Resource;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-12 20:57
 * @Description:
 */
@Component
@RestController
@RequestMapping("/mq")
public class MqController {
    @Resource
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/send")
    public String send(String queueName, String message) {
        // 发送消息, 如果没有队列会自动创建
        rabbitTemplate.convertAndSend(queueName, message);
        // 判断是否发送成功

        return "success";
    }

}