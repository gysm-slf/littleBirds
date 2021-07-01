package cn.littleBird.demo.old;

/**
 * 
 * @author famil
 *	异常父类，继承RuntimeException或Exception 运行时异常不用显示的去处理
 */
public class ExceptionDemo extends Exception{
	
	static final long serialVersionUID = -3387888993124229948L;
	
	public ExceptionDemo() {
		
	}
	
	public ExceptionDemo(String msg) {
		super(msg);
	}
}
