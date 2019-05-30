package com.niocoder.confirm;

import com.niocoder.utils.ConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {
    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionUtils connectionUtils = new ConnectionUtils().invoke();
        Connection connection = connectionUtils.getConnection();
        Channel channel = connectionUtils.getChannel();

        // 指定消息的投递模式，消息的确认模式
        channel.confirmSelect();

        // 4. 通过channel发送数据
        String msg = "Hello World for confirm";
        /**
         *      * @param exchange the exchange to publish the message to 交换机
         *      * @param routingKey the routing key 路由key
         *      * @param props other properties for the message - routing headers etc 消息的修饰
         *      * @param body the message body 消息体
         */
//        channel.basicPublish("test_confirm_exchange", "confirm.test", null, msg.getBytes());
        channel.basicPublish("test_confirm_exchange", "confirm.test", null, msg.getBytes());

//        // 5 添加确认监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("----------ack----------");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.err.println("----------nack----------");
            }
        });
    }
}
