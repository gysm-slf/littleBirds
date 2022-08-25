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

/**
 * 饿汉式
 * 缺点：提前占用资源
 *
 * 类加载的方式是按需加载，且只加载一次
 *
 * 因此，在上述单例类被加载时，就会实例化一个对象并交给自己的引用，供系统使用。单例就是该类只能返回一个实例。
 *
 * 换句话说，在线程访问单例对象之前就已经创建好了。再加上，由于一个类在整个生命周期中只会被加载一次，因此该单例类只会创建一个实例。
 *
 * 也就是说，线程每次都只能也必定只可以拿到这个唯一的对象。因此就说，饿汉式单例天生就是线程安全的。
 *
 * private static SingletonDemoQuick instance  --> static	为了只加载一次      还记不记得jvm类模板？
 */
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