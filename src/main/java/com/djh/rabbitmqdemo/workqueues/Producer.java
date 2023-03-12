package com.djh.rabbitmqdemo.workqueues;

import com.djh.rabbitmqdemo.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

/**
 * @author: Lenovo
 * @create: 2023/3/11 23:34
 * @description: workqueues
 */
public class Producer {
    public final static String QUEUE_NAME = "hello";
    @Test
    public void publish() throws Exception {
        // 创建连接工厂
        Connection connection = RabbitMQConnectionUtil.getConnection();

        // 创建通道
        Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        // 发送消息
        for (int i = 0; i < 10; i++) {
            String message = "Hello World!"+i;
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
        }
        System.out.println("消息发送成功！");
    }
}
