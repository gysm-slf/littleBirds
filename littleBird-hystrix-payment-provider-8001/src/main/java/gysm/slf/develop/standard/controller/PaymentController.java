package gysm.slf.develop.standard.controller;

import cn.littleBird.core.service.PaymentService;
import cn.littleBird.core.tools.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    //该注解可读取配置文件中配置的值
    @Value("${server.port}")
    private String serverPort;

    int i = 1;

    @RequestMapping(value = "/hystrix/paymentInfo/OK/{id}")
    public Object getPaymentOK(@PathVariable(value = "id") Long id) {
        String payment = paymentService.paymentInfo_OK(id);
        log.info("第"+(i++)+"********查询结果:" + payment.toString());
        return Response.getMsg("success,port:" + serverPort, payment);
    }

    @RequestMapping(value = "/hystrix/paymentInfo/TIMEOUT/{id}")
    public Object getPaymentTIMEOUT(@PathVariable(value = "id") Long id) {
        String payment = paymentService.paymentInfo_TIMEOUT(id);
        log.info("第"+(i++)+"********查询结果:" + payment.toString());
        return Response.getMsg("success,port:" + serverPort, payment);
    }

    @RequestMapping(value = "/hystrix/paymentInfo/EXCEPTION/{id}")
    public Object getPaymentEXCEPTION(@PathVariable(value = "id") Long id) {
        String payment = paymentService.paymentInfo_EXCEPTION(id);
        log.info("第"+(i++)+"********查询结果:" + payment.toString());
        return Response.getMsg("success,port:" + serverPort, payment);
    }

    //服务熔断
    @GetMapping(value = "/circuit/paymentInfo/{id}")
    public String paymentCircuitBreaker(@PathVariable(value = "id") Integer id){
        String result = paymentService.paymentCircuitBreaker(id);
        log.info("****result="+result);
        return result;
    }
}
