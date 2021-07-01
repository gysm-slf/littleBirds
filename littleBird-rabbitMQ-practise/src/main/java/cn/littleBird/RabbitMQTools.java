package cn.littleBird;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQTools {

    private static ConnectionFactory connectionFactory;

    static {
        //因为一般不会变所以在静态块中初始化时加载一次就够了
        //创建连接mq的连接工厂对象
        connectionFactory = new ConnectionFactory();
        //设置连接rabbitmq主机
        connectionFactory.setHost("192.168.88.130");
        //设置端口号
        connectionFactory.setPort(5672);
        //设置连接那个虚拟主主机
        //注意虚拟机名字  带不带/
        connectionFactory.setVirtualHost("littleBird");
        //设置访问虚拟主机的用户名和密码
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("admin");
        //获取连接对象
//        Connection connection = connectionFactory.newConnection();
    }
    public static Connection getConnection() throws IOException, TimeoutException {
        return connectionFactory.newConnection();
    }

    public static void releaseConnection(Channel channel,Connection connection){
        //注意关闭顺序 先释放频道channel再释放连接
        if (channel!=null){
            try {
                channel.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (TimeoutException e) {
                e.printStackTrace();
            }
        }
        if (connection!=null){
            try {
                connection.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
