package cn.littleBird.demo;

import cn.littleBird.core.tools.Array;
import com.sun.xml.internal.bind.api.impl.NameConverter;
import org.apache.commons.lang3.CharSet;
import org.springframework.util.ResourceUtils;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.Buffer;
import java.nio.IntBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import java.nio.ByteBuffer;

public class Test {
  /**
   * JVM
   *  方法区   栈 本地方法栈
   *   堆     程序计数器
   *
   * 方法区：类的模板（共享内容，如成员变量，构造函数，普通方法），还包括运行时常量池等字节码内容。线程共享
   * （注：方法区只是一种规范，不同jvm实现不同，最典型的为永久代和元空间。）
   * 堆：（存储）
   *
   * 栈：（运行）随线程的创建而创建（生命周期与线程一致，线程结束栈内存也就释放，线程私有且每个线程一个栈，不存在垃圾回收机制，因为线程结束直接就释放掉了）
   *    主要存储本地变量（输入输出参数、方法内的变量）、栈操作（出栈、入栈操作）、栈帧数据（类文件、方法）。
   *    Person person = new Person(); person在栈内 new Person在堆内 java方法 = 栈帧
   *    栈中的数据都是以栈帧格式存在的，栈帧是一个内存区块，当一个方法A被调用时就产生了一个栈帧F1并入栈，A方法又调用B方法，于是又产生栈帧F2并入栈 若F1需要出栈则只能等F2出栈（B方法执行完毕）。先进后出原则
   *    栈帧内部会存储局部变量表，操作数栈、动态链接、方法出口信息等
   * 本地方法栈：系统底层或者其它语言实现的（native修饰的方法）
   * 程序计数器（寄存器）：记录方法间的调用和执行情况，用来存储下一条指令的地址。线程私有且每个线程一份（类似排版值日表）
   *
   * @param args
   */
  public static void main(String[] args) {
    sellTickets s1 = new sellTickets();
    sellTickets s2 = new sellTickets();
    Thread t1 = new Thread(s1);
    Thread t2 = new Thread(s1);
    Thread t3 = new Thread(s1);
    Thread t4 = new Thread(s2);
    t1.start();
    t2.start();
    t3.start();
    t4.start();

    System.out.println("hello bug");



  }

  @org.junit.Test
  public void MapTest() {
    HashMap<String, String> map = new HashMap<>();
    map.put("zhangsan", "我是张三");
    map.put("lisi", "我是李四");
    Set<String> strings = map.keySet();
    Set<Map.Entry<String, String>> entries = map.entrySet();

  }

  @org.junit.Test
  public void IntegerDefaultValueTest() {
    System.out.println(new Integerdefault().i);
    System.out.println(new Integerdefault().j);
  }

  @org.junit.Test
  public void JSArrayTest() {
    Array<Integer> array = new Array<Integer>(new ArrayList());
    array.push(new Integer(2));
    array.push(new Integer(0));
    array.push(new Integer(8));
    array.unshift(5);
    array.unshift(1);
    array.pop();
    array.shift();
    array.print();
  }

  @org.junit.Test
  public void pathTest() throws IOException {
    System.out.println(ResourceUtils.getURL("classpath:").getPath());
    System.out.println(new Test().getClass().getResource("/").getPath());

    String size = "56KB";

    System.out.println(size.substring(0, size.length() - 2));
    System.out.println(size.substring(size.length() - 2).toLowerCase());
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
//        System.out.println(simpleDateFormat.format(new Date()));
//
//        System.out.println(ResourceUtils.getURL("classpath:").getPath() + "static/upload");
//        System.out.println(new PathTest().getClass().getResource("/").getPath());
    getPath();
//        System.out.println("测试".length());          //2
//        System.out.println("测试123".length());       //5
//        System.out.println("测试abc".length());       //5
//        System.out.println("测试--（）".length());     //6
  }

  //验证volatile不保证原子性
  @org.junit.Test
  public void VolatileUniformityTest() {
    MyDate date = new MyDate();

    change2 c = new change2(date);

    for (int i = 0; i < 20; i++) {
      Thread t = new Thread(c);
      t.start();
    }

    try {
      TimeUnit.MILLISECONDS.sleep(5000);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }

//        原子性为最终一致性   如果保证原子性，那么最终值应该为2w 但实际会存在丢数据
    System.out.println(date.num);
//        有原子性的自增  为什么atomicInteger不加synchronize就能保证原子性？
    System.out.println(date.atomicInteger);
  }

  //验证volatile可见性
  @org.junit.Test
  public void VolatileVisibleTest() {
    //此时main线程已经读取了date.num值  如果data.num没有被volatile修饰 那么将永远不会跳出下面的while循环
    MyDate date = new MyDate();

    change c = new change(date);
    Thread t1 = new Thread(c);
    t1.start();

    while (date.num == 0) {

    }

    System.out.println("num=" + date.num);
  }

  @org.junit.Test
  public void DeadLockDemoDay0521() {
    lock lock = new lock();
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          lock.m1();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, "t1").start();
    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          lock.m2();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    }, "t2").start();
  }

  @org.junit.Test
  public void demo1_Test() {

  }

  @org.junit.Test
  public void luotongTalent(){
    int three, five, eight, n;
    Scanner scanner = new Scanner(System.in);
    while (true){
      n = 45;
      three = 0; five = 0; eight = 0;
      for (int i = 0; i <= n; i++) {
        if(i % 3 == 0)
          three++;
        if(i % 5 == 0)
          five++;
        if(i % 8 == 0)
          eight++;
      }
      System.out.println(three + " " + five + " " + eight);
    }
  }


    @org.junit.Test
    public void demo4_Test(){

  }

  @org.junit.Test
  public void demo5_Test() {

  }

  @org.junit.Test
  public void demo6_Test() {

  }

  public void getPath() throws IOException {
    //当前项目下路径
    File file = new File("");
    String filePath = file.getCanonicalPath();
    System.out.println(filePath);

    //当前项目下xml文件夹
    File file1 = new File("");
    String filePath1 = file1.getCanonicalPath() + File.separator + "xml\\";
    System.out.println(filePath1);

    //获取类加载的根路径
    File file3 = new File(this.getClass().getResource("/").getPath());
    System.out.println(file3);

    //获取当前类的所在工程路径
    File file4 = new File(this.getClass().getResource("").getPath());
    System.out.println(file4);

    //获取所有的类路径 包括jar包的路径
    System.out.println(System.getProperty("java.class.path"));
  }

  @org.junit.Test
  public void nioRead() throws Exception {
    RandomAccessFile file = new RandomAccessFile("D:\\IDE\\projects\\iotest\\test1.txt", "rw");

    FileChannel channel = file.getChannel();
    //默认写模式
    ByteBuffer buf = ByteBuffer.allocate(1024);

    int readLen = channel.read(buf);

    while (readLen != -1) {
      System.out.println(readLen);
      //切换缓冲区读写模式
      buf.flip();
      while (buf.hasRemaining()) {
        //从缓存中每次读取一个Byte 并转换为char对象  char 2个字节  java使用Unicode char为2个字节 可以表示更多的字符  而 ascii为1个字节
        //这里为什么读一个字节后得到的却是ascii码？ 以4为例 存储在计算机中为1个字节 8位二进制数据 4在计算机中实际的存储为00110100 得到计算值52 将52转换为对应的字符则为4
        byte b = buf.get();
        System.out.println((char) b);
      }
      buf.clear();
      readLen = channel.read(buf);
    }
    file.close();
  }

  @org.junit.Test
  public void nioWrite() throws Exception {
    RandomAccessFile file = new RandomAccessFile("D:\\IDE\\projects\\iotest\\test2.txt", "rw");
    FileChannel channel = file.getChannel();
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    buffer.clear();
    buffer.put("write test".getBytes());
    buffer.flip();
    while (buffer.hasRemaining()) {
      channel.write(buffer);
    }
    channel.close();
  }

  @org.junit.Test
  public void nioTransTest() throws Exception {
    RandomAccessFile file1 = new RandomAccessFile("D:\\IDE\\projects\\iotest\\test2.txt", "rw");
    RandomAccessFile file2 = new RandomAccessFile("D:\\IDE\\projects\\iotest\\test3.txt", "rw");
    FileChannel channel1 = file1.getChannel();
    FileChannel channel2 = file2.getChannel();
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    long position = 0;
//        channel1.transferTo(position, channel1.size(), channel2);
    channel2.transferFrom(channel1, position, channel1.size());
    channel1.close();
    channel2.close();
  }

  /**
   * SocketChannel
   * wrap():将字节数组转换为ByteBuffer对象
   *
   * @throws Exception
   */
  @org.junit.Test
  public void ServerSocketChannelTest() throws Exception {
    int port = 8080;
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.socket().bind(new InetSocketAddress(port));
    ByteBuffer buffer = ByteBuffer.wrap("hello socket".getBytes());

    //设置阻塞模式 true:阻塞模式
    serverSocketChannel.configureBlocking(false);

    while (true) {
      SocketChannel accept = serverSocketChannel.accept();
      if (accept == null) {
        System.out.println("暂时没有连接");
        Thread.sleep(2000);
      } else {
        System.out.println("来自" + accept.getRemoteAddress() + "的一次访问");
        //重置缓冲区的指针为0(准备要操作数据了)
        buffer.rewind();
        accept.write(buffer);
        long readLen = accept.read(buffer);

        System.out.println(readLen);
        //切换缓冲区读写模式
        buffer.flip();
        while (buffer.hasRemaining()) {
          //从缓存中每次读取一个Byte 并转换为char对象  char 2个字节  java使用Unicode char为2个字节 可以表示更多的字符  而 ascii为1个字节
          //这里为什么读一个字节后得到的却是ascii码？ 以4为例 存储在计算机中为1个字节 8位二进制数据 4在计算机中实际的存储为00110100 得到计算值52 将52转换为对应的字符则为4
          byte b = buffer.get();
          System.out.print((char) b);
        }
        buffer.clear();
        buffer = ByteBuffer.wrap("hhhhh".getBytes());

        accept.close();
      }
    }
  }

  @org.junit.Test
  public void DataGramChannelSendDemo() throws Exception {
    DatagramChannel datagramChannel = DatagramChannel.open();
    InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1", 8080);
    //发送完以后会清空buffer 若要重复发送需要不断的new对象或者write
    //ByteBuffer buffer = ByteBuffer.wrap("Hi!".getBytes(StandardCharsets.UTF_8));
    while (true) {
      ByteBuffer buffer = ByteBuffer.wrap("Hi!".getBytes(StandardCharsets.UTF_8));
      datagramChannel.send(buffer, sendAddress);
      System.out.println("发送完成");
      Thread.sleep(1500);
    }
  }

  @org.junit.Test
  public void DataGramChannelReceiveDemo() throws Exception {
    DatagramChannel receiveChannel = DatagramChannel.open();
    InetSocketAddress inetSocketAddress = new InetSocketAddress(8080);
    receiveChannel.bind(inetSocketAddress);
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    while (true) {
      buffer.clear();
      SocketAddress socketAddress = receiveChannel.receive(buffer);
      buffer.flip();
      System.out.println(socketAddress.toString());
      System.out.println(Charset.forName("UTF-8").decode(buffer));
    }
  }

  @org.junit.Test
  public void DataGramChannelReadWriteDemo() throws Exception {
    DatagramChannel datagramChannel = DatagramChannel.open();
    datagramChannel.bind(new InetSocketAddress(8080));
    ByteBuffer sendBuffer = ByteBuffer.wrap("Hi".getBytes(StandardCharsets.UTF_8));

    datagramChannel.connect(new InetSocketAddress("127.0.0.1", 8080));

    //write or read 之前必须connect
    datagramChannel.write(sendBuffer);

    ByteBuffer receiveBuffer = ByteBuffer.allocate(1024);

    while (true) {
      receiveBuffer.clear();
      datagramChannel.read(receiveBuffer);
      receiveBuffer.flip();
      System.out.println(Charset.forName("UTF-8").decode(receiveBuffer));
    }
  }

  @org.junit.Test
  public void bufferDemo() throws Exception {
    IntBuffer buffer = IntBuffer.allocate(8);
    for (int i = 0; i < buffer.capacity(); i++) {
      buffer.put(i * 2);
    }
    buffer.flip();
    while (buffer.hasRemaining()) {
      System.out.println(buffer.get());
    }
  }

  /**
   * 缓冲区分片
   * 主要用于修改(自己理解的)
   *
   * @throws Exception
   */
  @org.junit.Test
  public void sliceDemo() throws Exception {
    IntBuffer buffer = IntBuffer.allocate(8);
    for (int i = 0; i < buffer.capacity(); i++) {
      buffer.put(i);
    }
    buffer.position(2);
    buffer.limit(5);
    IntBuffer sliceBuffer = buffer.slice();
    for (int i = 0; i < sliceBuffer.capacity(); i++) {
      sliceBuffer.put(i, sliceBuffer.get() * 10);
    }
    buffer.position(0);
    buffer.limit(buffer.capacity());
//        buffer.flip();
    while (buffer.hasRemaining()) { //or while (buffer.remaining() > 0)
      System.out.println(buffer.get());
    }
  }

  /**
   * 只读缓冲区会随着原缓冲区的变化而变化（类似引用）
   * @throws Exception
   */
  @org.junit.Test
  public void readOnlyBufferDemo() throws Exception {
    IntBuffer buffer = IntBuffer.allocate(8);
    for (int i = 0; i < buffer.capacity(); i++) {
      buffer.put(i);
    }
    IntBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
    buffer.flip();
    for (int i = 0; i < buffer.capacity(); i++) {
      buffer.put(i, buffer.get() * 10);
    }
    readOnlyBuffer.position(0);
    readOnlyBuffer.limit(buffer.capacity());
    while (readOnlyBuffer.remaining() > 0) {
      System.out.println(readOnlyBuffer.get());
    }
  }

  /**
   * 直接缓冲区
   * @throws Exception
   */
  @org.junit.Test
  public void allocateDirectDemo() throws Exception {
    FileInputStream fileInputStream = new FileInputStream("D:\\IDE\\projects\\iotest\\test1.txt");
    FileChannel inputChannel = fileInputStream.getChannel();
    FileOutputStream fileOutputStream = new FileOutputStream("D:\\IDE\\projects\\iotest\\test5.txt");
    FileChannel outputChannel = fileOutputStream.getChannel();
    ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
    while (true){
//      根据需要可选择清空buffer
//      buffer.clear();
      //读出来的数据写入到buffer中
      int read = inputChannel.read(buffer);
      if(read == 0){
        break;
      }
      //转换为读模式 将读出的数据写入
      buffer.flip();
      outputChannel.write(buffer);
    }
    inputChannel.close();
    outputChannel.close();
  }

  /**
   * selector demo
   */

  @org.junit.Test
  public void client() throws Exception {
    SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));

    socketChannel.configureBlocking(false);

    ByteBuffer buffer = ByteBuffer.allocate(1024);
    buffer.put(("This is data from client, date:" + new Date().toString()).getBytes());

    buffer.flip();
    socketChannel.write(buffer);

    buffer.clear();
    socketChannel.close();
  }

  @org.junit.Test
  public void server() throws Exception {
    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
    serverSocketChannel.bind(new InetSocketAddress(8080));

    serverSocketChannel.configureBlocking(false);

    Selector selector = Selector.open();

    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

    while (true){
      while (selector.select() > 0){
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while (iterator.hasNext()){
//        获取就绪操作
          SelectionKey channelKey = iterator.next();
//          判断什么操作
          if(channelKey.isAcceptable()){
//          获取连接(接收客户端发送的socketChannel)
            SocketChannel clientChannel = serverSocketChannel.accept();
//          切换非阻塞模式
            clientChannel.configureBlocking(false);
//          注册
            clientChannel.register(selector, SelectionKey.OP_READ);
          }else if(channelKey.isReadable()){
            SocketChannel channel = (SocketChannel) channelKey.channel();
            ByteBuffer clientBuffer = ByteBuffer.allocate(1024);
            int length = 0;
            while ((length = channel.read(clientBuffer)) > 0){
              clientBuffer.flip();
//            System.out.println(clientBuffer.get());
              System.out.println(new String(clientBuffer.array(), 0, length));
              clientBuffer.clear();
            }
          }
        }
        iterator.remove();
      }
    }
  }
  @org.junit.Test
  public void AsynchronousFileChannelDemo() throws Exception {
    AsynchronousFileChannel asynchronousFileChannel = AsynchronousFileChannel.open(Paths.get("F:\\test\\test.txt"), StandardOpenOption.READ);
    ByteBuffer buffer = ByteBuffer.allocate(1024);
    //从下标处开始读
    Future<Integer> future = asynchronousFileChannel.read(buffer, 0);
    while (!future.isDone());
    buffer.flip();
//    while (buffer.hasRemaining()){
//      System.out.println(buffer.get());
//    }
    byte[] data = new byte[buffer.limit()];
    buffer.get(data);
    System.out.println(new String(data));
    buffer.clear();
  }
}

class sellTickets implements Runnable {

  int totalTickets = 100;

  @Override
  public void run() {
    while (totalTickets > 0) {
      totalTickets--;
      if (totalTickets <= 0) {
        System.err.println("简直头皮发麻,totalTickets:" + totalTickets);
      }
      System.out.println(Thread.currentThread().getName() + "正在售出第" + (100 - totalTickets) + "张票，剩余" + totalTickets + "张票");
    }
  }
}

class Integerdefault {
  int i;
  Integer j;
}

class change2 implements Runnable {
  public MyDate date;

  public change2(MyDate date) {
    this.date = date;
  }

  @Override
  public void run() {
    for (int i = 0; i < 1000; i++) {
      date.addOne();
      date.addPlus();
    }
  }
}

class change implements Runnable {
  public MyDate date;

  public change(MyDate date) {
    this.date = date;
  }

  @Override
  public void run() {
    try {
      System.out.println("num=" + date.num);
      TimeUnit.SECONDS.sleep(3);
      date.addTo66();
      System.out.println("num=" + date.num);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }
}

class MyDate {
  volatile int num = 0;
  AtomicInteger atomicInteger = new AtomicInteger();

  public void addTo66() {
    this.num = 66;
  }

  public void addOne() {
    this.num++;
  }

  public void addPlus() {
    atomicInteger.getAndIncrement();
  }
}

class lock {
  Object lock1 = new Object();
  Object lock2 = new Object();

  public void m1() throws InterruptedException {
    synchronized (lock1) {
      TimeUnit.SECONDS.sleep(2);
      m2();
    }
  }

  public void m2() throws InterruptedException {
    synchronized (lock2) {
      TimeUnit.SECONDS.sleep(2);
      m1();
    }
  }
}