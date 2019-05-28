package com.niocoder.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Procuder {

    public static void main(String[] args) throws IOException, TimeoutException {

        // 1. 创建ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");

        // 2. 通过链接工厂创建连接
        Connection connection = factory.newConnection();

        // 3. 通过connection 创建一个channel
        Channel channel = connection.createChannel();

        // 4. 通过channel发送数据
        for (int i = 0; i < 3; i++) {
            String msg = "Hello World";
            /**
             *      * @param exchange the exchange to publish the message to 交换机
             *      * @param routingKey the routing key 路由key
             *      * @param props other properties for the message - routing headers etc 消息的修饰
             *      * @param body the message body 消息体
             */
            channel.basicPublish("", "hello", null, msg.getBytes());
        }

        // 5. 关闭连接
        channel.close();
        connection.close();
    }
}
