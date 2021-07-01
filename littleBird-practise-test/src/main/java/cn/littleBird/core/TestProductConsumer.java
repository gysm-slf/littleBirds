package cn.littleBird.core;

import java.util.concurrent.TimeUnit;

public class TestProductConsumer {

    public static void main(String[] args) {
//        Product p = new Product();
//        Pro pro = new Pro(p);
//        Cnm con = new Cnm(p);
//        Thread t1 = new Thread(pro);
//        Thread t2 = new Thread(con);
//        t1.start();
//        t2.start();
        DeadLock deadLock = new DeadLock();
    new Thread(()->{
        try {
            deadLock.sayYes();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }).start();
    new Thread(()->{
        try {
            deadLock.sayNo();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }).start();

    }
}

class DeadLock{
    Object lock1 = new Object();
    Object lock2 = new Object();

    public void sayYes() throws InterruptedException {
        synchronized (lock1){
            System.out.println("yes");
            TimeUnit.MILLISECONDS.sleep(2000);
            sayNo();
        }
    }

    public void sayNo() throws InterruptedException {
        synchronized (lock2){
            System.out.println("no");
            TimeUnit.MILLISECONDS.sleep(2000);
            sayYes();
        }
    }
}

class Pro implements Runnable {
    Product product;

    public Pro(Product p) {
        this.product = p;
    }

    @Override
    public void run() {
        synchronized (product){
            while (true) {
                if (product.num >= 3) {
                    product.notifyAll();
                    try {
                        product.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    product.num++;
                    System.out.println("生产了一个,当前number:" + product.num);
                }
            }
        }

    }
}

class Cnm implements Runnable {
    Product product;

    public Cnm(Product p) {
        this.product = p;
    }

    @Override
    public  void run() {
        synchronized(product){
            while (true) {
                if (product.num <= 0) {
                    product.notifyAll();
                    try {
                        product.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    product.num--;
                    System.out.println("消费了一个,当前number:" + product.num);
                }
            }
        }

    }
}

class Product {
    int num;

}