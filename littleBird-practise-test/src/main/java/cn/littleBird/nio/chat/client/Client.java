package cn.littleBird.nio.chat.client;

import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Scanner;

public class Client {

  private String name;

  public Client(String name){
    this.name = name;
  }

  public void start() throws Exception {
    //连接服务器
    SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8000));

    //接收服务器响应数据
    Selector selector = Selector.open();
    socketChannel.configureBlocking(false);
    socketChannel.register(selector, SelectionKey.OP_READ);

    new Thread(new ClientMessageAcceptThread(selector)).start();

    Scanner scanner = new Scanner(System.in);
    String message = "";
    while (scanner.hasNext()){
      message = scanner.next();
      socketChannel.write(Charset.forName("UTF-8").encode(name + "：" + message));
    }
  }

}
