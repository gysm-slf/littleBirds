package cn.littleBird.nio.chat.client;

import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class ClientMessageAcceptThread implements Runnable{

  private Selector selector;

  public ClientMessageAcceptThread(Selector selector){
    this.selector = selector;
  }

  @Override
  public void run() {
    try {
      while (true){
        while (selector.select() > 0){
          Set<SelectionKey> selectionKeys = selector.selectedKeys();
          Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
          while (keyIterator.hasNext()){
            SelectionKey key = keyIterator.next();

            //移除当前已经处理过的监听事件
            keyIterator.remove();

            if(key.isReadable()){
              handleMessage(selector, (SocketChannel) key.channel());
            }
            //移除当前已经处理过的监听事件
            //keyIterator.remove();
          }
        }
      }
    }catch (Exception e){
      e.printStackTrace();
    }
  }

  private void handleMessage(Selector selector, SocketChannel socketChannel) throws Exception {
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    int len = socketChannel.read(buffer);
    //循环读取客户端信息
    String message = "";
    if (len > 0){
      buffer.flip();
      message += Charset.forName("UTF-8").decode(buffer);
    }
    //注册channel，监听可读状态
    socketChannel.register(selector, SelectionKey.OP_READ);
    //将该客户端信息广播到其它所有客户端（群聊天室，所有人都应该看到信息）
    if(message.length() > 0){
      System.out.println(message);
    }
  }


}
