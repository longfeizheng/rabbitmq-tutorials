package com.niocoder.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class ConnectionUtils {
    private Connection connection;
    private Channel channel;

    public Connection getConnection() {
        return connection;
    }

    public Channel getChannel() {
        return channel;
    }

    public ConnectionUtils invoke() throws IOException, TimeoutException {
        // 1. 创建ConnectionFactory
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");

        // 2. 通过链接工厂创建连接
        connection = factory.newConnection();

        // 3. 通过connection 创建一个channel
        channel = connection.createChannel();
        return this;
    }
}