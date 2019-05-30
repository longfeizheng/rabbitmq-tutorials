package com.niocoder.dlx;

import com.niocoder.utils.ConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.util.HashMap;
import java.util.Map;

public class Consumer {
    public static void main(String[] args) throws Exception {
        ConnectionUtils connectionUtils = new ConnectionUtils().invoke();
        Connection connection = connectionUtils.getConnection();
        Channel channel = connectionUtils.getChannel();

        // 4. 声明

        // 这就是一个普通的交换机 和 队列 以及路由
        String exchangeName = "test_dlx_exchange";
//        String routingKey = "dlx.#";
        String routingKey = "aaa.#";
        String queueName = "test_dlx_queue";


        channel.exchangeDeclare(exchangeName, "topic", true, false, false, null);
        /**
         *      * @param queue the name of the queue 队列名字
         *      * @param durable true if we are declaring a durable queue (the queue will survive a server restart)  持久化
         *      * @param exclusive true if we are declaring an exclusive queue (restricted to this connection) 独占是消费，保证消息的顺序消费
         *      * @param autoDelete true if we are declaring an autodelete queue (server will delete it when no longer in use) 队列自动删除
         *      * @param arguments other properties (construction arguments) for the queue 扩展参数
         */

        Map<String, Object> agruments = new HashMap<String, Object>();
        agruments.put("x-dead-letter-exchange", "dlx.exchange");
        //这个agruments属性，要设置到声明队列上
        channel.queueDeclare(queueName, true, false, false, agruments);

        channel.queueBind(queueName, exchangeName, routingKey);

        //要进行死信队列的声明:
        channel.exchangeDeclare("dlx.exchange", "topic", true, false, null);
        channel.queueDeclare("dlx.queue", true, false, false, null);
        channel.queueBind("dlx.queue", "dlx.exchange", "#");
        // 6. 设置channel
//        channel.basicConsume(queueName, true, consumer);
        channel.basicConsume(queueName, false, new MyConsumer(channel));
    }
}
