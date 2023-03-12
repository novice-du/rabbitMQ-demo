package com.djh.rabbitmqdemo.pubsub;

import com.djh.rabbitmqdemo.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

/**
 * @author: Lenovo
 * @create: 2023/3/11 23:34
 * @description: pubsub
 */
public class Producer {
    private static final String EXCHANGE_NAME = "pubsub-exchange";
    public static final String QUEUE_NAME1 = "pubsub-one";
    public static final String QUEUE_NAME2 = "pubsub-two";

    @Test
    public void pubSub() throws Exception {
        //1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();
        //2.构建Channel
        Channel channel = connection.createChannel();
        //3.构建交换机 exchange类型为fanout（发布-订阅模式）
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);
        //4.构建队列
        channel.queueDeclare(QUEUE_NAME1,false,false,false,null);
        channel.queueDeclare(QUEUE_NAME2,false,false,false,null);
        //5.绑定交换机和队列，使用的是FANOUT类型的交换机，绑定方式是直接绑定
        channel.queueBind(QUEUE_NAME1,EXCHANGE_NAME,"");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"");
        //6.发消息到交换机
        channel.basicPublish(EXCHANGE_NAME,"",null,"publish/subscribe!".getBytes());
        System.out.println("消息成功发送！");
    }
}
