package cn.littleBird.demo.old;

public class Pra {

	public static void main(String[] args) {
		People p = new People();
		Thread t1=  new Thread(p,"张三");
		Thread t2=  new Thread(p,"李四");
		t1.start();
		t2.start();
	}
	
	

}

class Account{
	Double balance = 0.0;
	synchronized void saveMoney(Double money) {
		while(balance<3000) {
			balance+=money;
			System.out.println(Thread.currentThread().getName()+"存取了"+money+"元，当前余额为："+balance);
		}
	}
}

class People implements Runnable{
	Account account = new Account();
	int i = 0;

	@Override
	public void run() {
		for(int j=0;j<3;j++) {
			account.saveMoney(1000.0);
		}
	}
}
	

