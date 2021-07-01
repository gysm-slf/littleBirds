package cn.littleBird.demo.old;

public class TestThreadSingleton {

	public static void main(String[] args) {
		Singleton s1 = Singleton.getInstance();
		Singleton s2 = Singleton.getInstance();
		System.out.println(s1 == s2);
	}

}

/**
 * 懒汉式
 * @author famil
 *
 */
class Singleton {
	private Singleton() {

	}

	private static Singleton instance = null;

	//为了一个单例模式而使用synchronize不划算
//	变为安全模式则为单线程 单线程在并发情况下压力就很大
//	DCL (Double Check Lock 双端检锁机制)
	public static synchronized Singleton getInstance() {
		synchronized (Singleton.class) {
			if (instance == null) {
				instance = new Singleton();
			}
		}
		return instance;
	}
}

//饿汉式 
class SingletonDemoQuick {
	public static class SingletonHold {
		private static SingletonDemoQuick instance = new SingletonDemoQuick();
	}

	private SingletonDemoQuick() {
		super();
	}

	public static SingletonDemoQuick getInstance() {
		return SingletonHold.instance;
	}

}