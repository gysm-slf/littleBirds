//package cn.littleBird.demo.old;
//
//import java.lang.reflect.InvocationHandler;
//import java.lang.reflect.Method;
//import java.lang.reflect.Proxy;
//
//import com.su.utils.ProcessTrack;
//
//public class ProxyFactroy {
//
//	public static Object getProxyInstance(Object object) {
//		DynamicInvocationHandler dynamicInvocationHandler = new DynamicInvocationHandler();
//		dynamicInvocationHandler.bindProxy(object);
//		return Proxy.newProxyInstance(object.getClass().getClassLoader(), object.getClass().getInterfaces(),
//				dynamicInvocationHandler);
//	}
//
//	public static void main(String[] args) {
//		//代理的时候@Service就是ClothesFactory @Autowired NikeFactory
//		//这里返回的代理对象为什么是一个接口(自己理解的,记一下怕忘了)
//		//这里返回的是由spring新创建的一个类(创建的代理类)(Object,因为只有Object可以代理所有类),
//		//这个代理类实现了和被代理类同样的接口,所以可以转型为该接口,作为该接口的一个实现类
//		//
////		ClothesFactory proxy = (ClothesFactory) ProxyFactroy.getProxyInstance(new NikeFactory());
////		proxy.product();
//		ClothesFactory c = new NikeFactory();
//		c.product();
//	}
//
//}
//
//class DynamicInvocationHandler implements InvocationHandler{
//
//	private Object object;
//
//	public void bindProxy(Object object) {
//		this.object = object;
//	}
//
//	@Override
//	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//		String TransLogCode = ProcessTrack.getTransLogCode();
//		long processTrackStart = ProcessTrack.processTrackStart(object.getClass(), method.getName(),TransLogCode);
//		Object result = method.invoke(object, args);
//		ProcessTrack.processTrackEnd(object.getClass(), method.getName(), processTrackStart,TransLogCode);
//		return result;
//	}
//
//}
