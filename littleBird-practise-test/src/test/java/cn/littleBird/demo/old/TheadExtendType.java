package cn.littleBird.demo.old;

public class TheadExtendType extends Thread {
	public void run() {
		for (int i = 0; i < 100; i++) {
			System.err.println(Thread.currentThread().getName() + i);
		}
	}
}
