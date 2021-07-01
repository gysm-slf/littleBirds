package cn.littleBird.Direct;

import cn.littleBird.RabbitMQTools;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//订阅Provider
public class FansTwo {
    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQTools.getConnection();

        Connection connection = RabbitMQTools.getConnection();
        Channel channel = connection.createChannel();

        //通道(频道)绑定交换机
        channel.exchangeDeclare("logs_direct","direct");

        //临时队列
        String queue = channel.queueDeclare().getQueue();

        String routingKey = "info";
        String routingKey2 = "error";
        String routingKey3 = "warning";

        //绑定交换机和临时队列 可绑定多个
        channel.queueBind(queue,"logs_direct",routingKey);
        channel.queueBind(queue,"logs_direct",routingKey2);
        channel.queueBind(queue,"logs_direct",routingKey3);

        //消费消息
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("接收到的消息为：" + new String(body));
            }
        });
    }
}
