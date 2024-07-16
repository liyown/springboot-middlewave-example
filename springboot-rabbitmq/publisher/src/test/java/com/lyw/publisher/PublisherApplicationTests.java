package com.lyw.publisher;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PublisherApplicationTests {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init() {
        rabbitTemplate.setReturnsCallback(
                (ReturnedMessage returned) -> {
                    System.out.println("消息丢失: " + returned.getMessage());
                }
        );
        rabbitTemplate.setBeforePublishPostProcessors((message) -> {
            MessageProperties messageProperties = message.getMessageProperties();
            String messageId = messageProperties.getMessageId();
            System.out.println("messageId: " + messageId);
            return message;
        });

    }

    @Test
    public void testRabiitStart() throws InterruptedException {

        CorrelationData  correlationData = new CorrelationData("1");
        // 设置回调
        rabbitTemplate.setConfirmCallback((correlationData1, ack, cause) -> {
            System.out.println("cause: " + cause);
            System.out.println("correlationData: " + correlationData1);
            if (ack) {
                System.out.println("消息发送成功");
            } else {
                System.out.println("消息发送失败");
            }
        });
        // 队列名
        String queueName = "queue.lyw";


        // 发送消息, 如果没有队列会自动创建
        rabbitTemplate.convertAndSend("direct.lyw", queueName, "hello rabbitmq", correlationData);

    }

    @Test
    public void testRabiitMqDelayMessage() {
        CorrelationData  correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend("delay.direct", "delay.queue", "hello rabbitmq delay",
                                      (Message message) -> {
                                          message.getMessageProperties().setDelayLong(10000L);
                                          return message;
                                      }
                                      , correlationData);
    }

    @Test
    public void testRabiitMqDelayMessage2() {
        rabbitTemplate.convertAndSend("delay.direct", "delay.queue", "1812793267355439105", message -> {
            message.getMessageProperties().setDelayLong(10000L);
            return message;
        });
    }

}
