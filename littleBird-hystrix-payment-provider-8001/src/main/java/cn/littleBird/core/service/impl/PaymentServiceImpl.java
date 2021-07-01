package cn.littleBird.core.service.impl;

import cn.hutool.core.util.IdUtil;
import cn.littleBird.core.entity.Payment;
import cn.littleBird.core.service.PaymentService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentServiceImpl implements PaymentService {

    //服务端的服务降级（一般配置在客户端）
    @Override
    public String paymentInfo_OK(Long id) {
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_ok,id: "+id;
    }

//    表示三秒内响应为处理成功,如果交易处理时长超出三秒或交易异常则会执行paymentInfo_TIMEOUT_HANDLER该兜底方法
//    commandProperties = {
//            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
//    }
    @Override
    @HystrixCommand(fallbackMethod = "paymentInfo_TIMEOUT_HANDLER",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000")
    })
    public String paymentInfo_TIMEOUT(Long id) {
        try {
//            模拟复杂的业务处理需要三秒钟
//            TimeUnit.SECONDS.sleep(3);
            TimeUnit.MILLISECONDS.sleep(3000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_ok,id: O(∩_∩)O哈哈~超时啦";
    }

    public String paymentInfo_TIMEOUT_HANDLER(Long id){
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_ok,id: o(╥﹏╥)o~系统繁忙或响应超时";
    }

    @Override
    public String paymentInfo_EXCEPTION(Long id) {
        return "线程池："+Thread.currentThread().getName()+" paymentInfo_ok,id: "+id;
    }


    //服务熔断
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallback",commandProperties = {
            //是否开启断路器
            @HystrixProperty(name="circuitBreaker.enabled",value="true"),
            //请求次数 该属性用来设置在滚动时间窗中，断路器的最小请求数。例如：默认值 20 的情况下，如果滚动时间窗（默认值 10秒）内仅收到19个请求，即使这19个请求都失败了，断路器也不会打开。
            @HystrixProperty(name="circuitBreaker.requestVolumeThreshold",value="10"),
            //时间窗口期
            @HystrixProperty(name="circuitBreaker.sleepWindowInMilliseconds",value="10000"),
            //失败率达到多少时跳闸
            @HystrixProperty(name="circuitBreaker.errorThresholdPercentage",value="60")
            //在10秒钟的时间窗口进期内的10次请求，失败率达到60%会跳闸
    })
    public String paymentCircuitBreaker(@PathVariable(value = "id")Integer id){
        if (id < 0) {
            throw new RuntimeException("******id不能为负数******");
        }
        return Thread.currentThread().getName()+"\t调用成功，流水号位："+ IdUtil.simpleUUID();
    }

    public String paymentCircuitBreaker_fallback(@PathVariable(value = "id")Integer id) {
        return "id不能为负数，请稍后再试，o(╥﹏╥)o,id："+id;
    }
}
