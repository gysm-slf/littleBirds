package cn.littleBird.core.service;

import cn.littleBird.core.entity.Payment;
import cn.littleBird.core.tools.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "HYSTRIX-PAYMENT-SERVICE")
//@FeignClient(value = "HYSTRIX-PAYMENT-SERVICE",fallback = PaymentFallbackService.class)
//这样controller层的默认兜底注解和方法上HystrixCommand就都可以去掉了,但是又需要一个接口对应一个兜底方法（目前自己理解的是这样）
public interface PaymentService {

    @RequestMapping(value = "/hystrix/paymentInfo/OK/{id}")
    public Object getPaymentOK(@PathVariable(value = "id") Long id);

    @RequestMapping(value = "/hystrix/paymentInfo/TIMEOUT/{id}")
    public Object getPaymentTIMEOUT(@PathVariable(value = "id") Long id);

}
