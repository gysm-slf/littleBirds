package cn.littleBird.oneOnOne;

import com.rabbitmq.client.*;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Customer {

    //生产消息
    public static void main(String[] args) throws IOException, TimeoutException{

        //创建连接mq的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接rabbitmq主机
        connectionFactory.setHost("192.168.88.130");
        //设置端口号
        connectionFactory.setPort(5672);
        //设置连接那个虚拟主主机
//        System.out.println(connectionFactory.getVirtualHost());
        //注意虚拟机名字  带不带/
        connectionFactory.setVirtualHost("littleBird");
        //设置访问虚拟主机的用户名和密码
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");

        //获取连接对象
        Connection connection = connectionFactory.newConnection();

        //获取连接中通道
        final Channel channel = connection.createChannel();

//        通道中同时只能存在一个消息  即每一次只能一个一个消息的消费
        channel.basicQos(1);

        //通道绑定对应消息队列
        //参数1:队列名称 如果队列不存在自动创建
        //参数2:用来定义队列特性是否要持化 true 持久化队列false 不持久化 如果不持久化 那么重启rabbitMQ 队列就会消失 队列中未被消费的消息页会消失 持久化也仅仅是持久化队列 消息不会保存
        //参数3:exclusive 是否独占队列true 独占队列 false  如果独占则不能被其它channel绑定，一般都是不独占
        //参数4:auttoDelete: 是否在消费完后自动删除队列 true 目动删  false 不自动删除 如果已经已不自动删除跑过了 在跑自动删除的队列 需要先删除之前的队列或新建  因为已经有了一个非持久化的  在去声明同属性的自动删除的会冲突
        //参数5:额外附加参数
//                              定义到的队列名
        channel.queueDeclare("demo1_queue", false, false, false, null);
        //消费消息
        //参数1: 消费哪个队列的消息
        //参数2: 开始消息的自动确认机制 该消费者拿到消息后就当成功了    一般都是为否 然后手动确认消息（可以理解为成功触发了回调函数即消息肯定被成功消费了）
        //参数3: 消费时的回调接口
        //思考下 消费者如何消费 有耗时之类的间隔吗 比如 队列中刚有发布消息就立刻消费吗
//                              实际发布到的队列名
        channel.basicConsume("demo1_queue", false, new DefaultConsumer(channel){

            @Override //参数4：消息队列中取出的消息
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
//               参数1 消息的标志 参数2 是否开启多消息同时确认
                channel.basicAck(envelope.getDeliveryTag(),false);
                System.out.println("接收到的消息为：" + new String(body));
            }
        });

        //如果想要持续监听通道,可以选择不关闭   一般customer模型中都是不关闭的
//        channel.close();
//        connection.close();
    }
}