package cn.littleBird.demo.old;

public class TestThreadSellTickets {

	/*
	 * 如何实现线程的安全，线程的同步机制 方式一：同步代码块 synchronized(同步监视器){ 需要被同步的代码 }
	 * 1.共享数据：多个线程共同操作的同一个数据（变量） 2.同步监视器：由一个类的对象来充当，哪个线程获取此监视器，谁就执行大括号里的同步代码 俗称：锁
	 * 方式二：同步方法 public synchronized method1(){} 隐式锁：为this
	 * 
	 */
	public static void main(String[] args) {

//		SellTicketsImp1 sti = new SellTicketsImp1();
//		Thread t1 = new Thread(sti, "一号售票窗口 ");
//		Thread t2 = new Thread(sti, "二号售票窗口 ");
//		Thread t3 = new Thread(sti, "三号售票窗口 "); 
//		t1.start();
//		t2.start();
//		t3.start();

		SellTicketsImp2 sti2 = new SellTicketsImp2();    
		SellTicketsImp2 sti21 = new SellTicketsImp2();   
		SellTicketsImp2 sti22 = new SellTicketsImp2();   
		Thread t4 = new Thread(sti2, "四号售票窗口 ");
		Thread t5 = new Thread(sti21, "五号售票窗口 ");
		Thread t6 = new Thread(sti22, "六号售票窗口 "); 
		t4.start();
		t5.start();
		t6.start();

//		SellTicketsT1 st7 = new SellTicketsT1();
//		SellTicketsT1 st8 = new SellTicketsT1();
//		SellTicketsT1 st9 = new SellTicketsT1();
//		st7.setName("售票窗口七 ");st8.setName("售票窗口八 ");st9.setName("售票窗口九 ");
//		st7.start(); st8.start(); st9.start();

//		SellTicketsT2 st10 = new SellTicketsT2(); // X
//		SellTicketsT2 st11 = new SellTicketsT2();
//		SellTicketsT2 st12 = new SellTicketsT2();
//		st10.setName("售票窗口十 ");
//		st11.setName("售票窗口十一 ");
//		st12.setName("售票窗口十二 ");
//		st10.start();
//		st11.start();
//		st12.start();
	}

}

//实现方式同步代码块
class SellTicketsImp1 implements Runnable {
	int total = 100;

	@Override
	public void run() {
		while (total > 0) {
			synchronized (this) {
				if (total <= 0) {
					System.out.println(Thread.currentThread().getName() + " 票已售完，请您明天再来！");
					break;
				}
				System.out.println(Thread.currentThread().getName() + "正在出售第" + (101 - total) + "张票，" + "已成功售出第"
						+ (101 - total) + "张票" + ",还剩余" + (--total) + "张票！");
			}
		}
	}
}

//实现方式同步方法  X
class SellTicketsImp2 implements Runnable {
	int total = 100;

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while (total > 0) {
			show();
		}
	}

	public synchronized void show() {
		if (total <= 0) {
			System.out.println(Thread.currentThread().getName() + " 票已售完，请您明天再来！");
		} else {
			System.out.println(Thread.currentThread().getName() + "正在出售第" + (101 - total) + "张票，" + "已成功售出第"
					+ (101 - total) + "张票" + ",还剩余" + (--total) + "张票！");
		}
	}
}

//继承方式同步代码块
class SellTicketsT1 extends Thread {
	public static int total = 100;
	static Object obj = new Object(); // 必须让 三个售票窗口 共用同一个监视器

	public void run() {
		while (total > 0) {
			synchronized (obj) {
				if (total <= 0) {
					System.out.println(Thread.currentThread().getName() + " 票已售完，请您明天再来！");
					break;
				}
				System.out.println(Thread.currentThread().getName() + "正在出售第" + (101 - total) + "张票，" + "已成功售出第"
						+ (101 - total) + "张票" + ",还剩余" + (--total) + "张票！");
			}
		}
	}
}

//继承方式同步代码方法   使用this错误！！！！ 三个锁
class SellTicketsT2 extends Thread {
	public static int total = 100;

	public void run() {
		while (true) {
			show();
		}
	}

	public synchronized void show() {
		if (total <= 0) {
			System.out.println(Thread.currentThread().getName() + " 票已售完，请您明天再来！");
		} else {
			System.out.println(Thread.currentThread().getName() + "正在出售第" + (101 - total) + "张票，" + "已成功售出第"
					+ (101 - total) + "张票" + ",还剩余" + (--total) + "张票！");
		}
	}
}