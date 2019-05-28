package com.niocoder.quickstart;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer {
    public static void main(String[] args) throws Exception {
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

        // 4. 创建一个队列
        String queueName = "hello";
        /**
         *      * @param queue the name of the queue 队列名字
         *      * @param durable true if we are declaring a durable queue (the queue will survive a server restart)  持久化
         *      * @param exclusive true if we are declaring an exclusive queue (restricted to this connection) 独占是消费，保证消息的顺序消费
         *      * @param autoDelete true if we are declaring an autodelete queue (server will delete it when no longer in use) 队列自动删除
         *      * @param arguments other properties (construction arguments) for the queue 扩展参数
         */
        channel.queueDeclare(queueName, true, false, false, null);

        // 5. 创建消费者
        QueueingConsumer consumer = new QueueingConsumer(channel);

        // 6. 设置channel
        channel.basicConsume(queueName,true,consumer);

        while (true){
            // 7. 获取消息
            QueueingConsumer.Delivery delivery = consumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.err.println("消费端: " + msg);
        }
    }
}
