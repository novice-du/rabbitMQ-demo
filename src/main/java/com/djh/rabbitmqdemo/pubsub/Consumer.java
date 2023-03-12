package com.djh.rabbitmqdemo.pubsub;

import com.djh.rabbitmqdemo.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @author: Lenovo
 * @create: 2023/3/11 23:43
 * @description:
 */
public class Consumer {

    @Test
    public void consume1() throws Exception {
        //1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();
        //2.构建Channel
        Channel channel = connection.createChannel();
        //3.构建队列
        channel.queueDeclare(Producer.QUEUE_NAME1,false,false,false,null);
        //4. 监听消息
        DefaultConsumer callback = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1号获取到消息：" + new String(body,"UTF-8"));
            }
        };
        channel.basicConsume(Producer.QUEUE_NAME1,true,callback);
        System.out.println("消费者1号开始监听队列");

        System.in.read();
    }
}
