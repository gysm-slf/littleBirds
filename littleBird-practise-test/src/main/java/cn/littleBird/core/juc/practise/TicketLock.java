package cn.littleBird.core.juc.practise;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class TicketLock {

    public static void main(String[] args) {
//        Thread t1 = new Thread();
//        Thread t2 = new Thread();
//        Thread t3 = new Thread();


        Ticket t = new Ticket();
        //线程的几种状态 新建 就绪 等待 超时等待 阻塞 销毁
        //匿名内部类的方式
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i<300;i++){
                    t.sale();
                }
            }
        }, "一号售票机").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i<400;i++){
                    t.sale();
                }
            }
        }, "二号售票机").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0;i<500;i++){
                    t.sale();
                }
            }
        }, "三号售票机").start();
        //简化版  拉姆达表达式
//        new Thread(() -> {for (int i = 0;i<400;i++) t.sale();},"1").start();
//        new Thread(() -> {for (int i = 0;i<400;i++) t.sale();},"2").start();
//        new Thread(() -> {for (int i = 0;i<400;i++) t.sale();},"3").start();
    }
}

class Ticket{

    private int total = 1000;
                //可重入锁
    Lock lock = new ReentrantLock();
//    CopyOnWriteArraySet
//    CopyOnWriteArrayList
//    ConcurrentHashMap
    public void sale(){
        lock.lock();
        try{
            if(total>0){
                System.out.println(Thread.currentThread().getName()+"售出第"+(1001-total)+"张票,剩余"+(--total)+"张票");
            }
        }/*catch(Exception e){
            e.printStackTrace();
        }*/finally{
            lock.unlock();
        }
    }
}