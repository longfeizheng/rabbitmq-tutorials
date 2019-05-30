package com.niocoder.limit;

import com.niocoder.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

public class Consumer {
    public static void main(String[] args) throws Exception {
        ConnectionUtils connectionUtils = new ConnectionUtils().invoke();
        Connection connection = connectionUtils.getConnection();
        Channel channel = connectionUtils.getChannel();

        // 4. 声明
        String exchangeName = "test_qos_exchange";
        String exchangeType = "topic";
        String queueName = "test_qos_queue";
        String routingKey = "qos.*";

        channel.exchangeDeclare(exchangeName, exchangeType, true, false, false, null);
        /**
         *      * @param queue the name of the queue 队列名字
         *      * @param durable true if we are declaring a durable queue (the queue will survive a server restart)  持久化
         *      * @param exclusive true if we are declaring an exclusive queue (restricted to this connection) 独占是消费，保证消息的顺序消费
         *      * @param autoDelete true if we are declaring an autodelete queue (server will delete it when no longer in use) 队列自动删除
         *      * @param arguments other properties (construction arguments) for the queue 扩展参数
         */
        channel.queueDeclare(queueName, true, false, false, null);

        channel.queueBind(queueName, exchangeName, routingKey);

        //1 限流方式  第一件事就是 autoAck设置为 false
        channel.basicQos(0, 1, false);
        // 6. 设置channel
//        channel.basicConsume(queueName, true, consumer);
        channel.basicConsume(queueName, false, new MyConsumer(channel));
    }
}
