package cn.littleBird.demo.old;

public class TestThreadPrintOneToHundred {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrintThread pt = new PrintThread();
		Thread t1 = new Thread(pt);
		Thread t2 = new Thread(pt);
		t1.start();
		t2.start();
	}

}

class PrintThread implements Runnable{
	int i = 0;
	@Override
	public void run() {
		while(true) {
			synchronized(this){
				notify();
				if(i>100) {
					break;
				}
				try {
					Thread.currentThread().sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName()+":"+i++);
				try {
					wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
