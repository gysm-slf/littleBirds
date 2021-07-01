package cn.littleBird.oneOnOne;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Provider {

    //生产消息
    @Test
    public void SendMessage() throws IOException, TimeoutException {
        //创建连接mq的连接工厂对象
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接rabbitmq主机
        connectionFactory.setHost("192.168.88.130");
        //设置端口号
        connectionFactory.setPort(5672) ;
        //设置连接那个虚拟主主机
//        System.out.println(connectionFactory.getVirtualHost());
        //注意虚拟机名字  带不带/
        connectionFactory.setVirtualHost("littleBird") ;
        //设置访问虚拟主机的用户名和密码
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin") ;

        //获取连接对象
        Connection connection = connectionFactory.newConnection();

        //获取连接中通道
        Channel channel = connection.createChannel();

        //通道绑定对应消息队列
        //参数1:队列名称 如果队列不存在自动创建
        //参数2:用来定义队列特性是否要持化 true 持久化队列false_ 不持久化
        //参数3:exclusive 是否独占队列true 独占队列 false
        //参数4:auttoDelete: 是否在消费完后自动删除队列 true 目动删  false 不自动删除
        //参数5:额外附加参数
        channel.queueDeclare("demo1_queue",false,false,false,null) ;

        //发布消息
        //参数1: 交换机名称 参数2:队列名称 参数3:传递消息额外设置 参数4:消息的具体内容
        channel.basicPublish("","demo1_queue",null,"hello rabbit 11 =(:з」∠)_兔子".getBytes());

        channel.close();
        connection.close();
    }
}
