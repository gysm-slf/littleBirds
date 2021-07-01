package cn.littleBird.demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

//验证volatile可见性
public class VolatileVisibleTest {
    public static void main(String[] args) {

        //此时main线程已经读取了date.num值  如果data.num没有被volatile修饰 那么将永远不会跳出下面的while循环
        MyDate date = new MyDate();

        change c = new change(date);
        Thread t1 = new Thread(c);
        t1.start();

        while (date.num==0){

        }

        System.out.println("num="+date.num);
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
