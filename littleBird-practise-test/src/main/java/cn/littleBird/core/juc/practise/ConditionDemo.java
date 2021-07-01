package cn.littleBird.core.juc.practise;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ConditionDemo {
    public static void main(String[] args) {
        ConditionDemo demo = new ConditionDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i =0;i<2;i++){
                        demo.print5();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i =0;i<2;i++){
                        demo.print10();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    for (int i =0;i<2;i++){
                        demo.print15();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();
    }

    private int flag = 1;
    Lock lock = new ReentrantLock();
//    为一个锁分配三把钥匙
    Condition key1 = lock.newCondition();
    Condition key2 = lock.newCondition();
    Condition key3 = lock.newCondition();

    public void print5() throws InterruptedException {
        TimeUnit.SECONDS.sleep(3);
        lock.lock();
        try{
            //判断
            while (flag!=1){
                key1.await();
            }
            //干活儿
            for (int i=0;i<5;i++){
                System.out.println(Thread.currentThread().getName()+"-"+(i+1));
            }
            //通知
            flag=2;
            key2.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
    public void print10() throws InterruptedException {
        System.out.println("其实这里已经来过了");
        lock.lock();
        System.out.println("lock其实这里已经来过了");
        try{
            //判断
            while (flag!=2){
                //进到这里后 因为print5里面暂停了几秒 线程会先到这里 1！=2 true   此时第二把钥匙key2把锁lock给释放了
                key2.await();
            }
            //干活儿
            for (int i=0;i<10;i++){
                System.out.println(Thread.currentThread().getName()+"-"+(i+1));
            }
            //通知
            flag=3;
            key3.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
    public void print15() throws InterruptedException {
        lock.lock();
        try{
            //判断
            while (flag!=3){
                key3.await();
            }
            //干活儿
            for (int i=0;i<15;i++){
                System.out.println(Thread.currentThread().getName()+"-"+(i+1));
            }
            //通知
            flag=1;
            key1.signal();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }
    }
}
