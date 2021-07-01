package cn.littleBird.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import cn.littleBird.proxy.ProcessTrack;

/**
 * 1.静态代理
 * 静态代理:由程序员创建代理类或者特定的工具自动生成源代码在对其进行编译
 * 静态代理----真是方法和代理方法的名字和参数等相同,方法体不同即可,真实对象和代理对象实现相同的接口
 *
 * 优点:
 * 解耦合,简单
 * 缺点:
 * 代理类和委托类实现了相同的接口,代理类通过委托类实现了相同的方法,代码重复,如果接口增加一个方法,除了所有实现类需要实现这个方法之外,还需要所有的代理类也实现这个方法,增加了代码的复杂度.
 *
 * 2.动态代理
 * 动态代理:在程序运行时用反射机制,动态创建
 *
 * 1.JDK动态代理 — 接口级别代理
 * 步骤:
 * ① 获取真实对象上的所有接口列表
 * ② 确定要生成的代理
 * ③ 根据需要实现的接口信息,在代码中动态生成该代理类的字节码
 * ④ 将对应的字节码转换成对应的class对象
 * ⑤ 创建InvocationHandler实例Handler.用来处理代理所有方法调用
 * ⑥ 代理的class对象已创建的Handler对象为参数,实例化一个代理对象
 * 特点:真实对象和代理对象实现相同的接口
 * 效率:JDK动态代理是基于反射的,效率比较低
 * 好处:它是官方自带的,在使用是无需自己再导入jar包
 *
 * 2.CGLIB代理 — 方法级别代理
 * 步骤:
 * ① 查找A上的所有非final的public类型的方法定义
 * ② 将这些方法的定义转换成字节码
 * ③ 将组成的字节码转换成相对应的代理的class对象
 * ④ 实现MethodInterceptor接口,用来处理对代理类上所有方法的请求
 * 特点:基于继承,代理对象继承真实的对象
 * 效率:基于字节码文件动态生成,效率高
 * 坏处:第三方提供,需要导入jar包
 *
 */

public class ProxyFactroy {

	public static Object getProxyInstance(Object object) {
		DynamicInvocationHandler dynamicInvocationHandler = new DynamicInvocationHandler();
		dynamicInvocationHandler.bindProxy(object);
		return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(),
				dynamicInvocationHandler);
	}
	
	public static void main(String[] args) {
		//代理的时候@Service就是ClothesFactory @Autowired NikeFactory
		//这里返回的代理对象为什么是一个接口(自己理解的,记一下怕忘了)
		//这里返回的是由spring新创建的一个类(创建的代理类)(Object,因为只有Object可以代理所有类),
		//这个代理类实现了和被代理类同样的接口,所以可以转型为该接口,作为该接口的一个实现类
		//
		ClothesFactory proxy = (ClothesFactory) ProxyFactroy.getProxyInstance(new NikeFactory());
		proxy.product();
		proxy.product2();
//		ClothesFactory c = new NikeFactory();
//		c.product();
//		c.product2();
	}

}

class DynamicInvocationHandler implements InvocationHandler{

	private Object object;
	
	public void bindProxy(Object object) {
		this.object = object;
	}
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		String TransLogCode = ProcessTrack.getTransLogCode();
		long processTrackStart = ProcessTrack.processTrackStart(object.getClass(), method.getName(),TransLogCode);
		Object result = method.invoke(object, args);
		ProcessTrack.processTrackEnd(object.getClass(), method.getName(), processTrackStart,TransLogCode);
		return result;
	}
	
}
