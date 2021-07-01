package cn.littleBird.core.lock;

import java.util.concurrent.TimeUnit;

public class DeadLockDemo {

    private static Object lock1 = new Object();
    private static Object lock2 = new Object();

    public static void main(String[] args) throws InterruptedException {

        DeadLockDemo dead = new DeadLockDemo();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dead.T1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread1").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    dead.T2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "thread2").start();

    }

    public void T1() throws InterruptedException {
        synchronized (lock1){
            TimeUnit.SECONDS.sleep(1);
            synchronized (lock2){
                TimeUnit.SECONDS.sleep(1);
            }
        }


    }

    public void T2() throws InterruptedException {
        synchronized (lock2){
            TimeUnit.SECONDS.sleep(1);
            synchronized (lock1){
                TimeUnit.SECONDS.sleep(1);
            }
        }


    }
}
