package cn.littleBird.proxy;


//static  静态
//dynamic 动态

interface ClothesFactory {
	String name = "father";
	public void product();
	public void product2();
}

class NikeFactory implements ClothesFactory{
	String name = "son";
	String a = "s";
	@Override
	public void product() {
		for (int i = 0; i < 20; i++) {
			System.out.println("Nike produce a batch Nike Air Force ...");
		}
	}

	@Override
	public void product2() {
		System.out.println("product2");
	}

	public void a() {
		System.out.println("子类可以有自己的行为");
	}
	
}

//class StaticProxyFactory implements ClothesFactory{
//	private ClothesFactory factory;
//
//	public StaticProxyFactory(ClothesFactory factory) {
//		this.factory = factory;
//	}
//
//	@Override
//	public void product() {
//		System.out.println("代理工厂做了一些准备工作");
//		factory.product();
//		System.out.println("代理工厂做了一些收尾工作");
//	}
//}
//
//public class StaticProxyDemo {
//
//	public static void main(String[] args) {
//		//静态代理
//		NikeFactory nike = new NikeFactory();
//		StaticProxyFactory proxy = new StaticProxyFactory(nike);
//		proxy.product();
//
//
//
//
//
//
//		//动态代理
//
//
//	}
//
//}
