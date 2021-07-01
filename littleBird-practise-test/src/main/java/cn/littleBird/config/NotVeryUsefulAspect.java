package cn.littleBird.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class NotVeryUsefulAspect {

    @Pointcut("execution(* cn.littleBird.core.service.impl..*.*(..))")
    public void anyPublicMethod(){

    }

    @Before("anyPublicMethod()")
    public void before(){
        System.out.println("before method ..");
    }
}
