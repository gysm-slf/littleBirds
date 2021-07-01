package cn.littleBird.demo.old;
/*
 * Thread类的常用方法
 * 1.start():启动线程
 * 2.run():子线程要执行的代码
 * 3.currentThead():静态的，调取当前的线程
 * 4.setName():
 * 5.getName():
 * 6.yield():调用此方法的当前线程释放当前CPU的执行权，比如两个线程都是输出0~100,如果线程1输出的数可以被10整除，那么线程1.yield() 线程1: 1 2 3 4 线程2: 1 2 3 线程1： 5 6 7 8 9 10 线程2：5 6 但是也有可能线程一又抢回了执行权 继续执行 11 12 13
 * 7.join():在A线程中调用B线程的join()方法，表示，当执行到此方法，A线程停止执行，直至B线程执行完毕 
 * 8.isAlive():判断线程是否还存活
 * 9.sleep(long l):显示的让当前线程睡眠l毫秒
 * 10.setPriority(int num):设置执行优先级  1~10
 * 11.getPriority():返回执行优先级  1~10
 * 12.wait():
 * 13.notify():
 * 14.notifyAll():
 * */
class SubThread extends Thread{
	public void run() {
		for(int i = 0 ; i<200;i++) {
			System.out.println(Thread.currentThread().getName()+" "+i+" ");
		}
	}
}

public class TestThread {

	public static void main(String[] args) throws InterruptedException {
		Thread.currentThread().setName("threadMain");
		SubThread st1 = new SubThread();
		SubThread st2 = new SubThread();
		st1.setName("thread001");
		st2.setName("thread002");
		st1.start();
		st2.start();
		for(int i = 0 ; i<100;i++) {
			if(i%10==0) {
				Thread.currentThread().yield();
			}
			if(i%20==0) {
				st2.join();
			}
			System.out.println(Thread.currentThread().getName()+" "+i+" ");
		}
	}
	
	/**
	 * @Title: sumOfPrimeNumber
	 * @Description:返回num1~num2之间的质数和,并输出范围内的质数num1必须小于num2
	 * @author sulongfei
	 * @date 2019年7月1日
	 */
	public static int sumOfPrimeNumber(int num1, int num2,String threadName) {
		if(num1-num2>0) {
			System.out.println("输入参数不正确,参数规则是num1<num2");
		}
		boolean flag;
		int sum = 0;
		for (int i = num1; i <= num2; i++) {
			flag = true;
			for (int j = 2; j < i; j++) {
				if (i % j == 0) {
					flag = false;
					break;
				}
			}
			if (flag) {
				System.out.println(threadName+" " + i + " ");
				sum += i;
			}
		}
		return sum;
//		System.out.println("从" + num1 + "~" + num2 + "之间的质数和为:" + sum);
	}
	
}
