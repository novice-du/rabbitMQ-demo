package com.djh.rabbitmqdemo.workqueues;

import com.djh.rabbitmqdemo.utils.RabbitMQConnectionUtil;
import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;

/**
 * @author: Lenovo
 * @create: 2023/3/11 16:04
 * @description:
 */
public class Consumer {

    /**
     * 消费者1号
     */
    @Test
    public void consume1() throws Exception {
        //1. 获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        //2. 构建Channel
        Channel channel = connection.createChannel();

        //3. 构建队列
        channel.queueDeclare(Producer.QUEUE_NAME,false,false,false,null);

        channel.basicQos(1);

        //4. 监听消息
        DefaultConsumer callback = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    //睡0.1s来模拟该消费者消费能力较强
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("消费者1号获取到消息：" + new String(body,"UTF-8"));
                //手动ack
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        channel.basicConsume(Producer.QUEUE_NAME,false,callback);
        System.out.println("1号开始监听队列");

        System.in.read();
    }

    /**
     * 消费者2号
     */
    @Test
    public void consume2() throws Exception {
        //1. 获取连接对象
        Connection connection = RabbitMQConnectionUtil.getConnection();

        //2. 构建Channel
        Channel channel = connection.createChannel();

        //3. 构建队列
        channel.queueDeclare(Producer.QUEUE_NAME,false,false,false,null);

        channel.basicQos(1);

        //4. 监听消息
        DefaultConsumer callback = new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                try {
                    //睡0.5s来模拟该消费者消费能力较弱
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("消费者2号获取到消息：" + new String(body,"UTF-8"));
                //手动ack
                channel.basicAck(envelope.getDeliveryTag(),false);
            }
        };
        //将第二个参数设为false意为关闭自动ack
        channel.basicConsume(Producer.QUEUE_NAME,false,callback);
        System.out.println("2号开始监听队列");

        System.in.read();
    }
}
