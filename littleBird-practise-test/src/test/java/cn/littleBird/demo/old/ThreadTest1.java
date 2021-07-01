package cn.littleBird.demo.old;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadTest1 {

	public static void main(String[] args) {
		System.out.println("231321");
//		clerk c = new clerk();
//		producer1 pro = new producer1(c);
//		producer1 pro2 = new producer1(c);
//		producer1 pro3 = new producer1(c);
//		customer cus = new customer(c);
//		Thread t1 = new Thread(pro);
//		Thread t2 = new Thread(cus);
//		Thread t3 = new Thread(pro2);
//		Thread t4 = new Thread(pro3);
//		t1.setName("厂商一");
//		t1.start();
//		t2.start();
//		t2.setName("消费者2.");
//		t3.start();
//		t3.setName("厂商二");
//		t4.start();
//		t4.setName("厂商三");
		//使用线程池方式创建线程，创建50个线程池↓
		//Executors.newFixedThreadPool返回的是ThreadPoolExecutor对象，该对象的父类继承了ExecutorService
		ExecutorService service = Executors.newFixedThreadPool(50);
		System.out.println("hello");
		int a = 10;
	}

}

class clerk {
	int kucun = 0;

	public synchronized void prod() {
		if (kucun < 20) {
			kucun++;
			System.out.println(Thread.currentThread().getName()+"A厂开始生产第" + kucun + "件大衣");
			notify();
		}else {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

	public synchronized void sale() {
		if (kucun > 0) {
			System.out.println(Thread.currentThread().getName()+"消费者购买了第" + kucun + "件大衣");
			kucun--;
			notify();
		}else {
			try {
				wait();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

class producer1 implements Runnable {
	private clerk pro_clerk;

	public producer1(clerk c) {
		this.pro_clerk = c;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			pro_clerk.prod();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}

class customer implements Runnable {
	private clerk cus_clerk;
	public customer(clerk c) {
		this.cus_clerk = c;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (true) {
			cus_clerk.sale();
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
