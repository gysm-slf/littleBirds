package gysm.slf.develop.standard.controller;

import cn.littleBird.core.entity.Payment;
import cn.littleBird.core.service.PaymentService;
import cn.littleBird.core.tools.Response;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@DefaultProperties(defaultFallback = "globalFallback",commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
})
@RestController
public class FeignOrderController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/hystrix/paymentInfo/OK/{id}")
    @HystrixCommand
    public Object getPaymentOK(@PathVariable(value = "id") Long id) {
        int a = 5/0;
        return paymentService.getPaymentOK(id);
    }

    //    表示1.5秒内响应为处理成功,如果交易处理时长超出1.5秒或交易异常（如代码里有5/0）则会执行paymentInfo_TIMEOUT_HANDLER该兜底方法
    @HystrixCommand(fallbackMethod = "getPaymentTIMEOUT_HANDLER",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "1500")
    })
    @GetMapping(value = "/consumer/hystrix/paymentInfo/TIMEOUT/{id}")
    public Object getPaymentTIMEOUT(@PathVariable(value = "id") Long id) {
        return paymentService.getPaymentTIMEOUT(id);
    }

    public String getPaymentTIMEOUT_HANDLER(@PathVariable(value = "id") Long id){
        return "线程池："+Thread.currentThread().getName()+" ,o(╥﹏╥)o~系统繁忙或响应超时";
    }

    //全局兜底方法,这是默认的兜底方法,需要在对应类上加DefaultProperties注解指定该方法
    //如果该类中的某一方法的HystrixCommand注解没有指定fallbackMethod而是单独一个@HystrixCommand,则降级时会默认调用全局兜底方法
    public String globalFallback(){
        return "globalFallback ,请求异常，请稍后再试";
    }

}
