package cn.littleBird.nio.chat.server;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

/**
 * selector.keys 返回当前所有注册在selector中channel的selectionKey
 * selector.selectedKeys() 返回注册在selector中等待IO操作(及有事件发生)channel的selectionKey。
 *
 * SelectionKey在被轮询后需要remove()。注册过的channel信息会以SelectionKey的形式存储在selector.keys()中。也就是说每次select()后的selectedKeys迭代器中是不能还有成员的，但keys()中的成员是不会被删除的(以此来记录channel信息)。
 * selector不会自己删除selectedKeys()集合中的selectionKey，那么如果不人工remove()，将导致下次select()的时候selectedKeys()中仍有上次轮询留下来的信息，这样必然会出现错误。
 *
 */
public class Server {

  public void start() throws Exception {
    Selector selector = Selector.open();

    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress(8000));

    serverSocketChannel.configureBlocking(false);

    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    System.out.println("server is start ..");

    while (true){
      while (selector.select() > 0){
        //获取可用的channel
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
        while (keyIterator.hasNext()){
          SelectionKey key = keyIterator.next();

          keyIterator.remove();

          //监听到有客户端接入 获取该客户端并注册到selector
          if(key.isAcceptable()){
            acceptSocketChannel(selector, serverSocketChannel);
//            System.out.println((SocketChannel)key.channel());
          }
          if(key.isReadable()){
            handleMessage(selector, (SocketChannel) key.channel());
          }
          //移除当前已经处理过的监听事件
          //keyIterator.remove();
        }
      }
    }

  }

  private void acceptSocketChannel(Selector selector, ServerSocketChannel serverSocketChannel) throws Exception {
    SocketChannel socketChannel = serverSocketChannel.accept();
    socketChannel.configureBlocking(false);
    socketChannel.register(selector, SelectionKey.OP_READ);
    socketChannel.write(Charset.forName("UTF-8").encode("您已加入聊天室，请注意您的隐私安全"));
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
      sendMessage(selector, socketChannel, message);
    }
  }

  private void sendMessage(Selector selector, SocketChannel socketChannel, String message) throws Exception {
    Set<SelectionKey> keys = selector.keys();
    Iterator<SelectionKey> keyIterator = keys.iterator();
    Channel elseChannel = null;
    while (keyIterator.hasNext()){
      elseChannel = keyIterator.next().channel();
      //广播消息时排除自己
      if(elseChannel instanceof SocketChannel && elseChannel != socketChannel)
        ((SocketChannel)elseChannel).write(Charset.forName("UTF-8").encode(message));
    }
  }

  public static void main(String[] args) throws Exception {
    new Server().start();
  }
}
