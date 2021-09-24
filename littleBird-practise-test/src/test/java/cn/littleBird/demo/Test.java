package cn.littleBird.demo;

import cn.littleBird.core.tools.Array;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class Test {

    @org.junit.Test
    public void ThreadTest() {
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
    public void IntegerDefaultValueTest(){
        System.out.println(new Integerdefault().i);
        System.out.println(new Integerdefault().j);
    }

    @org.junit.Test
    public void JSArrayTest(){
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
    public void VolatileUniformityTest(){
        MyDate date = new MyDate();

        change2 c = new change2(date);

        for (int i=0;i<20;i++){
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
    public void VolatileVisibleTest(){
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
    public void DeadLockDemoDay0521(){
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
    public void demo1_Test(){

    }
    @org.junit.Test
    public void demo2_Test(){

    }
    @org.junit.Test
    public void demo3_Test(){

    }
    @org.junit.Test
    public void demo4_Test(){

    }
    @org.junit.Test
    public void demo5_Test(){

    }
    @org.junit.Test
    public void demo6_Test(){

    }

    public void getPath() throws IOException {
        //当前项目下路径
        File file = new File("");
        String filePath = file.getCanonicalPath();
        System.out.println(filePath);

        //当前项目下xml文件夹
        File file1 = new File("");
        String filePath1 = file1.getCanonicalPath()+File.separator+"xml\\";
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

class Integerdefault{
    int i;
    Integer j;
}

class change2 implements Runnable{
    public MyDate date;

    public change2(MyDate date){
        this.date=date;
    }

    @Override
    public void run() {
        for (int i=0;i<1000;i++){
            date.addOne();
            date.addPlus();
        }
    }
}

class change implements Runnable{
    public MyDate date;

    public change(MyDate date){
        this.date=date;
    }

    @Override
    public void run() {
        try {
            System.out.println("num="+date.num);
            TimeUnit.SECONDS.sleep(3);
            date.addTo66();
            System.out.println("num="+date.num);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

class MyDate{
    volatile int num = 0;
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addTo66(){
        this.num= 66;
    }

    public void addOne(){
        this.num++;
    }

    public void addPlus(){
        atomicInteger.getAndIncrement();
    }
}

class lock{
    Object lock1 = new Object();
    Object lock2 = new Object();

    public void m1() throws InterruptedException {
        synchronized (lock1){
            TimeUnit.SECONDS.sleep(2);
            m2();
        }
    }

    public void m2() throws InterruptedException {
        synchronized (lock2){
            TimeUnit.SECONDS.sleep(2);
            m1();
        }
    }
}