package cn.littleBird.demo.old;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MyAutowired {
	String value() default "";
}
