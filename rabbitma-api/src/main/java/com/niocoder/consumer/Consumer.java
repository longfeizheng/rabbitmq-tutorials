package com.niocoder.consumer;

import com.niocoder.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer {
    public static void main(String[] args) throws Exception {
        ConnectionUtils connectionUtils = new ConnectionUtils().invoke();
        Connection connection = connectionUtils.getConnection();
        Channel channel = connectionUtils.getChannel();

        // 4. 声明
        String exchangeName = "test_consumer_exchange";
        String exchangeType = "topic";
        String queueName = "test_consumer_queue";
        String routingKey = "consumer.*";

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
        // 5. 创建消费者

        // 6. 设置channel
//        channel.basicConsume(queueName, true, consumer);
        channel.basicConsume(queueName, true, new MyConsumer(channel));
    }
}
