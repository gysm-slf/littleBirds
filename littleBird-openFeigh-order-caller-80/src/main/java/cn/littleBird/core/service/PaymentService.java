package cn.littleBird.core.service;

import cn.littleBird.core.entity.Payment;
import cn.littleBird.core.tools.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(value = "PAYMENT-SERVICE")
public interface PaymentService {

    @RequestMapping(value = "/addPayment")
    public Object addPayment(@RequestBody Payment payment);

    @RequestMapping(value = "/getPayment/{id}")
    public Object getPaymentById(@PathVariable(value = "id") Long id) ;

    @GetMapping(value = "/payment/timeout")
    public String paymentFeignTimeout();
}
