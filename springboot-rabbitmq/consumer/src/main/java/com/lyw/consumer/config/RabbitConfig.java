package com.lyw.consumer.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.rabbit.retry.RepublishMessageRecoverer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: liuyaowen
 * @poject: spring_middleware.java
 * @create: 2024-07-13 16:03
 * @Description:
 */
@Configuration
public class RabbitConfig {

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout.lyw");
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct.lyw");
    }

    @Bean
    public Queue queue() {
        return new Queue("queue.lyw");
    }

    @Bean
    public Binding binding(Queue queue, FanoutExchange fanoutExchange) {
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    public Binding binding2(Queue queue, DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with("queue.lyw");
    }

    @Bean
    public MessageRecoverer republishMessageRecoverer(RabbitTemplate rabbitTemplate){
        return new RepublishMessageRecoverer(rabbitTemplate, "error.direct", "error");
    }


    @Bean
    public Queue delayQueue() {
        return new Queue("delay.queue");
    }

    @Bean
    public DirectExchange delayExchange() {
        return ExchangeBuilder.directExchange("delay.direct").delayed().durable(true).build();
    }

    @Bean Binding delayBinding(Queue delayQueue, DirectExchange delayExchange) {
        return BindingBuilder.bind(delayQueue).to(delayExchange).with("delay.queue");
    }
}
