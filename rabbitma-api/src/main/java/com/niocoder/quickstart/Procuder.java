package com.niocoder.quickstart;

import com.niocoder.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Procuder {

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionUtils connectionUtils = new ConnectionUtils().invoke();
        Connection connection = connectionUtils.getConnection();
        Channel channel = connectionUtils.getChannel();

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
