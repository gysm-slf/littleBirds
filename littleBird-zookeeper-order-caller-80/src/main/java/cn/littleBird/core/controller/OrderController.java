package cn.littleBird.core.controller;

import cn.littleBird.core.entity.Payment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
public class OrderController {
//    之前一台机器可固定机器,多台机器可配置服务名，并且开启了轮询策略（轮询在ApplicationContextConfig中的@LoadBalanced）
//    public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://PAYMENT-SERVICE";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/customer/payment/zk")
    public String paymentInfo(){
        //这里的调用地址为http://localhost:8004/payment/zk,返回类型为String
        return restTemplate.getForObject(PAYMENT_URL + "/payment/zk",String.class);

    }
}
