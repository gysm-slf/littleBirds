package cn.littleBird.topic;

import cn.littleBird.RabbitMQTools;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//订阅Provider
public class FansOne {
    public static void main(String[] args) throws IOException, TimeoutException {
        RabbitMQTools.getConnection();

        Connection connection = RabbitMQTools.getConnection();
        Channel channel = connection.createChannel();

        //通道(频道)绑定交换机
        channel.exchangeDeclare("topics","topic");

        //临时队列
        String queue = channel.queueDeclare().getQueue();

        //user.*  只会接收到两个单词组成的   user.*   例: user.save user.delete   (user.save.ok是不会接收的)
        //user.#  以user.开头的都会接收
//        *.user.*  只能匹配三个单词的且中间为user的
//        #.user.*  前面任意多个单词 倒数第二个为user 后面还有一个单词
        String routingKey = "user.*.*";

        //绑定交换机和队列
        channel.queueBind(queue,"topics",routingKey);

        //消费消息
        channel.basicConsume(queue,true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("接收到的消息为：" + new String(body));
            }
        });
    }
}
