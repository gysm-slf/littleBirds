package cn.littleBird.core.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
public class PaymentController {

    //该注解可读取配置文件中配置的值
    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/payment/zk")
    public String paymentzk(){
        return "srpingcloud with zookeeper: " + serverPort+"\t"+ UUID.randomUUID().toString();
    }


}
