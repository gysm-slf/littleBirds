package cn.littleBird.demo.old;

class SubClass1 extends Thread{
	public void run(){
		SubClass2 sc2=new SubClass2();
		sc2.setName("subClass2-1");
		sc2.start();
		for(int i = 0 ; i < 200 ;i++) {
			System.out.println(Thread.currentThread().getName()+" "+i+" ");
			if(i==23) {
				try {
					System.out.println("--------------------");
					sc2.join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}

class SubClass2 extends Thread{
	public void run(){
		for(int i = 0 ; i < 50 ;i++) {
			if(i%2!=0) {
				System.out.println(Thread.currentThread().getName()+" "+i+" ");
			}
		}
	}
}

public class testThread1 {
	
	public static void main(String[] args) {
		int i = 0;
		while(i<10) {
			i++;
			if(i == 3) {
				break;
			}else {
//				account.saveMoney(1000.0);
				System.out.println(456);
			}
		}
//		SubClass1 sc1 = new SubClass1();
//		sc1.setName("subClass1-1");
//		sc1.start();
	}

}
