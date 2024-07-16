package com.lyw.consumer;

import com.rabbitmq.client.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

class ConsumerApplicationTests {

    private static ConnectionFactory connectionFactory;

    @BeforeAll
    static void beforeAll() {

        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.208.128");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("user");
        connectionFactory.setPassword("password");
        connectionFactory.setVirtualHost("/hmall");




    }

    @Test
    void contextLoads() throws IOException, TimeoutException {
        Connection connection = connectionFactory.newConnection();
        System.out.println(connection);

        Channel channel = connection.createChannel();
        Channel channel2 = connection.createChannel();

        // 设置PrefetchCount=1，表示每次只接收一条消息
        channel.basicQos(250);

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("接收到消息：" + new String(body));
            }
        };

        channel.basicConsume("queue.lyw", true, consumer);
        channel.basicConsume("queue.lyw", true, consumer);

        channel2.basicConsume("queue.lyw", true, consumer);


        System.in.read();
    }

}
