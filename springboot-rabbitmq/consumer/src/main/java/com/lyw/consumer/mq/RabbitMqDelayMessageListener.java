package com.lyw.consumer.mq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-15 16:12
 * @Description:
 */
@Component
public class RabbitMqDelayMessageListener {

    @RabbitListener(queues = "delay.queue")
    public void onMessage(String message) {
        System.out.println("接收到延迟消息: " + message);
    }
}
