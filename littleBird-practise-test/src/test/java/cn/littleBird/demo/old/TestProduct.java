//package cn.littleBird.demo.old;
//
//import com.su.utils.ProcessTrack;
//
//public class TestProduct {
//
//	//set/getPriority()设置获取优先级
//	public static void main(String[] args) {
////		long processTrackStart = ProcessTrack.processTrackStart(new TestProduct().getClass(), "main");
//		Market m = new Market();
//		Producer p1 = new Producer(m);
//		Consumer c1 = new Consumer(m);
//		Thread pt1 = new Thread(p1, "一号工厂");
//		Thread pt2 = new Thread(p1, "二号工厂");
//		Thread pt3 = new Thread(p1, "三号工厂");
//		Thread ct1 = new Thread(c1, "一号消费者选手");
//		Thread ct2 = new Thread(c1, "二号消费者选手");
//		pt1.start();
//		pt2.start();
////		pt3.start();
////		ct1.start();
//		ct2.start();
////		ProcessTrack.processTrackEnd(new TestProduct().getClass(), "main", processTrackStart);
//	}
//
//}
//
////生产者
//class Producer implements Runnable {
//	Market market;
//
//	public Producer(Market market) {
//		this.market = market;
//	}
//
//	@Override
//	public void run() {
//		System.out.println("生产厂商已开始生产商品");
//		while (true) {
//			market.yieldAProduct();
//		}
//	}
//	// 生产方法
////	public synchronized void yieldAProduct() {
////		if(market.productNumber >= 20) {
////			try {
////				wait();
////			} catch (InterruptedException e) {
////				e.printStackTrace();
////			}
////		}else {
////			market.productNumber++;
////			try {
////				Thread.currentThread().sleep(100);
////			} catch (InterruptedException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////			System.out.println(Thread.currentThread().getName()+"正在生产第"+market.productNumber+"件商品");
////		}
////	}
//}
//
////消费者
//class Consumer implements Runnable {
//	Market market;
//
//	public Consumer(Market market) {
//		this.market = market;
//	}
//
//	@Override
//	public void run() {
//		System.out.println("消费者开始购买商品");
//		while (true) {
//			market.sellingProducts();
//		}
//	}
//
//	// 售出方法
////	public synchronized void sellingProducts() {
////		if(market.productNumber<=0) {
////			try {
////				wait();
////			} catch (InterruptedException e) {
////				e.printStackTrace();
////			}
////		}else {
////			try {
////				Thread.currentThread().sleep(10);
////			} catch (InterruptedException e) {
////				// TODO Auto-generated catch block
////				e.printStackTrace();
////			}
////			System.out.println(Thread.currentThread().getName()+"购买了第"+market.productNumber+"件商品");
////			market.productNumber--;
////			notify();
////		}
////	}
//}
//
////
//class Market {
//	int productNumber = 0;
//
//	public synchronized void yieldAProduct() {
//		if (productNumber >= 20) {
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		} else {
//			productNumber++;
//			try {
//				Thread.currentThread().sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(Thread.currentThread().getName() + "成功生产 1 件商品，当前共有" + productNumber + "件商品");
//			notifyAll();
//			try {
//				wait(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//
//	public synchronized void sellingProducts() {
//		if (productNumber <= 0) {
//			try {
//				wait();
//			} catch (InterruptedException e) {
//				e.printStackTrace();
//			}
//		} else {
//			try {
//				Thread.currentThread().sleep(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			System.out.println(Thread.currentThread().getName() + "购买了 1 件商品，此刻商场库存剩余" + (--productNumber) + "件");
//			notifyAll();
//			try {
//				wait(100);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		}
//	}
//}