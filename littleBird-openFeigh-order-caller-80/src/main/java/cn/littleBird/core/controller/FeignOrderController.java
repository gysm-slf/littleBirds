package cn.littleBird.core.controller;

import cn.littleBird.core.entity.Payment;
import cn.littleBird.core.service.PaymentService;
import cn.littleBird.core.tools.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class FeignOrderController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping(value = "/consumer/payment/create")
    public Object create(Payment payment) {
        return paymentService.addPayment(payment);
    }

    @GetMapping(value = "/consumer/payment/query/{id}")
    public Object queryById(@PathVariable(value = "id") Long id) {
        return paymentService.getPaymentById(id);
    }

    @GetMapping(value = "/consumer/payment/timeout")
    public String paymentFeignTimeout(){
        return paymentService.paymentFeignTimeout();
    }
}
