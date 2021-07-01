package cn.littleBird.Direct;

import cn.littleBird.RabbitMQTools;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//广播（发布与订阅）模型 一个消息同时被多个消费者订阅 应用较为广泛
//需要创建fanout类型的交换机
public class Provider {
    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = RabbitMQTools.getConnection();
        Channel channel = connection.createChannel();

        //将通道声明指定交换机  //参数1：交换机名称   参数2：交换机类型   fanout：广播类型
        channel.exchangeDeclare("logs_direct","direct");

        String routingKey = "error";

        for (int i=0;i<10;i++){
            //发送消息
            channel.basicPublish("logs_direct",routingKey,null,("direct type message"+routingKey+Integer.toString(i)).getBytes());
        }

        RabbitMQTools.releaseConnection(channel,connection);


    }
}
