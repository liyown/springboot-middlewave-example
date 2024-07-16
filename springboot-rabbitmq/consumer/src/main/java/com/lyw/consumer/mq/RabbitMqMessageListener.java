package com.lyw.consumer.mq;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-12 20:42
 * @Description:
 */
@Slf4j
@Component
public class RabbitMqMessageListener {

    @RabbitListener(queues = "queue.lyw")
    public void onMessage(String message) {
        log.info("接收到消息: {}", message);
        //
        throw new MessageConversionException("消息转换异常");
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "work.queue1"),
            exchange = @Exchange(value = "work.exchange", type = "direct"),
            key = {"work.queue1"})
    )
    public void onMessageWorkerQueue1(String message) throws InterruptedException {

        Thread.sleep(100);
        Thread thread = Thread.currentThread();
        log.info("工作队列1接收到消息: {}，时间：{},线程{}", message, LocalDateTime.now(), thread.getName());
    }

    @RabbitListener(queues = "work.queue1")
    public void onMessageWorkerQueue2(String message) throws InterruptedException {
        Thread.sleep(200);
        Thread thread = Thread.currentThread();
        log.info("工作队列2接收到消息: {}，时间：{},线程{}", message, LocalDateTime.now(), thread.getName());
    }
}
