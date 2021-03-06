package com.niocoder.returnmsg;

import com.niocoder.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionUtils connectionUtils = new ConnectionUtils().invoke();
        Connection connection = connectionUtils.getConnection();
        Channel channel = connectionUtils.getChannel();


        // 4. 通过channel发送数据
        String msg = "Hello World for return";

//        // 5 添加确认监听
        channel.addReturnListener((replyCode, replyText, exchange, routingKey, properties, body) -> {
            System.err.println("replyCode: " + replyCode);
            System.err.println("replyText: " + replyText);
            System.err.println("exchange: " + exchange);
            System.err.println("routingKey: " + routingKey);
            System.err.println("properties: " + properties);
            System.err.println("body: " + new String(body));
        });
        /**
         *      * @param exchange the exchange to publish the message to 交换机
         *      * @param routingKey the routing key 路由key
         *      * @param props other properties for the message - routing headers etc 消息的修饰
         *      * @param body the message body 消息体
         */
//        channel.basicPublish("test_confirm_exchange", "confirm.test", null, msg.getBytes());
        channel.basicPublish("test_return_exchange", "aaa.test",true, null, msg.getBytes());
    }
}
