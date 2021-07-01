package cn.littleBird.demo.old;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

@Retention(RetentionPolicy.RUNTIME)
public @interface AnnotationDemo {
	String value() default "hello";
	String name();
	
}
//如果注解有成员，在使用注解时，需要指明成员的值
//自定义注解必须配上注解的信息处理流程（使用反射）才有意义
//没有默认值必须赋值，有默认值可以省略
//元数据：对现有数据的一个修饰  例：string name ="gysm.slf" string 是类型 name是名字 都是对 "gysm.slf"的修饰 "gysm.slf"为元数据
//元注解相同，是对注解进行修饰的注解 用来修饰注解的注解
//1.@AnnotationDemo    String value()   2.@AnnotationDemo    String value() default "hello"
//jdk5.0支持的四个元注解
//元注解 1:Retention指定注解的生命周期 1)RetentionPolicy.SOURCE. 2)RetentionPolicy.CLASS(默认). 3).RetentionPolicy.RUNTIME(运行时注解，可以通过反射的时候获取到)
//元注解 2:Target 指定注解可以用来修饰什么,默认全部 自定义注解一般都会指定1和2
//元注解 3:Documented 使用javadoc生成文档时，会提取该注解
//元注解 4:Inherited 具有继承性，父类使用了被该元注解修饰的注解，子类自动使用该注解
//jdk8可重复注解
@AnnotationDemo(value="helloWorld",name="demoAnnotation")
class AnnotationTest {
	
	@MyAutowired(value = "备注是嘻嘻哈哈")
	private String xxhh;
	
	public static void main(String[] args) throws NoSuchFieldException, SecurityException {
		Collection<String> col = new LinkedList();
		Collection<String> col2 = new LinkedList();
	}
}