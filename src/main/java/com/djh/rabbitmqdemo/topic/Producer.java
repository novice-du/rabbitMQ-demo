package com.djh.rabbitmqdemo.topic;

import com.djh.rabbitmqdemo.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.junit.Test;

/**
 * @author: Lenovo
 * @create: 2023/3/12 13:12
 * @description: topic
 */
public class Producer {

    private static final String EXCHANGE_NAME = "topic-exchange";
    public static final String QUEUE_NAME1 = "topic-one";
    public static final String QUEUE_NAME2 = "topic-two";

    @Test
    public void pubSub() throws Exception {
        //1.获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();
        //2.构建Channel
        Channel channel = connection.createChannel();
        //3.构建交换机 exchange类型为topic（主题模式）
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        //4.构建队列
        channel.queueDeclare(QUEUE_NAME1,false,false,false,null);
        channel.queueDeclare(QUEUE_NAME2,false,false,false,null);
        //5.绑定交换机和队列，使用的是topic类型的交换机
        channel.queueBind(QUEUE_NAME1,EXCHANGE_NAME,"*.orange.*");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"*.*.rabbit");
        channel.queueBind(QUEUE_NAME2,EXCHANGE_NAME,"lazy.#");
        //6.发消息到交换机
        channel.basicPublish(EXCHANGE_NAME,"big.orange.cat",null,"大橘猫!".getBytes());
        channel.basicPublish(EXCHANGE_NAME,"small.white.rabbit",null,"小白兔!".getBytes());
        channel.basicPublish(EXCHANGE_NAME,"lazy.dog.dog.dog",null,"懒狗狗!".getBytes());

        System.out.println("消息成功发送！");
    }
}
