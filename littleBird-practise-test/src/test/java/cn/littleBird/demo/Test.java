package cn.littleBird.demo;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test {

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
//        t4.start();
    }

    @org.junit.Test
    public void nioRead() throws Exception{
        RandomAccessFile file = new RandomAccessFile("D:\\IDE\\projects\\iotest\\test1.txt", "rw");

        FileChannel channel = file.getChannel();
        //默认写模式
        ByteBuffer buf = ByteBuffer.allocate(1024);

        int readLen = channel.read(buf);

        while(readLen != -1){
            System.out.println(readLen);
            //切换缓冲区读写模式
            buf.flip();
            while(buf.hasRemaining()){
                //从缓存中每次读取一个Byte 并转换为char对象  char 2个字节  java使用Unicode char为2个字节 可以表示更多的字符  而 ascii为1个字节
                //这里为什么读一个字节后得到的却是ascii码？ 以4为例 存储在计算机中为1个字节 8位二进制数据 4在计算机中实际的存储为00110100 得到计算值52 将52转换为对应的字符则为4
                byte b = buf.get();
                System.out.println((char)b);
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
        while (buffer.hasRemaining()){
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
}

class sellTickets implements Runnable{

    int totalTickets = 100;

    @Override
    public void run() {
        while (totalTickets > 0){
            totalTickets--;
            if(totalTickets<=0){
                System.err.println("简直头皮发麻,totalTickets:"+totalTickets);
            }
            System.out.println(Thread.currentThread().getName()+"正在售出第"+(100-totalTickets)+"张票，剩余"+totalTickets+"张票");
        }
    }
}