package com.djh.rabbitmqdemo.routing;

import com.djh.rabbitmqdemo.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

/**
 * @author: Lenovo
 * @create: 2023/3/12 12:33
 * @description: routing
 */
public class Producer {

    private static final String EXCHANGE_NAME = "routing-exchange";
    public static final String QUEUE_NAME1 = "routing-one";
    public static final String QUEUE_NAME2 = "routing-two";

    @Test
    public void pubSub() throws Exception {
        //1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();
        //2.构建Channel
        Channel channel = connection.createChannel();
        //3.构建交换机 exchange类型为direct（路由模式）
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        //4.构建队列
        channel.queueDeclare(QUEUE_NAME1,false,false,false,null);
        channel.queueDeclare(QUEUE_NAME2,false,false,false,null);
        //5.绑定交换机和队列，使用的是DIRECT类型的交换机
        channel.queueBind(QUEUE_NAME1,EXCHANGE_NAME,"ORANGE");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"BLACK");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"GREEN");
        //6.发消息到交换机
        channel.basicPublish(EXCHANGE_NAME,"ORANGE",null,"橙子!".getBytes());
        channel.basicPublish(EXCHANGE_NAME,"BLACK",null,"黑葡萄!".getBytes());
        //因为没有构建WHITE的路由规则，所以该消息不会路由到任何队列中
        channel.basicPublish(EXCHANGE_NAME,"WHITE",null,"兔子!".getBytes());
        System.out.println("消息成功发送！");
    }
}
