package cn.littleBird.nio;

import java.io.FileNotFoundException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class FileChannelDemo1 {

    public static void main(String[] args) throws Exception {

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

    public static void toUNICODE(String s)
    {
        StringBuilder sb=new StringBuilder();
        for(int i=0;i<s.length();i++)
        {
            sb.append("\\" + "u"+Integer.toHexString(s.charAt(i)));
        }
        System.out.println(sb.toString());
    }
}
