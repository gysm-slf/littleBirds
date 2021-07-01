package cn.littleBird;

import java.util.concurrent.TimeUnit;

public class DeadLockDemoDay0521 {
    public static void main(String[] args) {
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
