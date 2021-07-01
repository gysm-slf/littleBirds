package cn.littleBird.core.controller;

import cn.littleBird.core.entity.Payment;
import cn.littleBird.core.service.PaymentService;
import cn.littleBird.core.tools.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@RestController
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DiscoveryClient discoveryClient;

    //该注解可读取配置文件中配置的值
    @Value("${server.port}")
    private String serverPort;

    @RequestMapping(value = "/addPayment", method = RequestMethod.POST)
    //RequestBody将请求参数转换为json格式,加了此注解后仅能被客户端调用,无法直接浏览器地址栏访问了
    //因为调用者(客户端)请求参数为json格式,这里是为了解析json数据绑定到后台对象上(SpringMVC通过使用
    //
    //HandlerAdapter 配置的HttpMessageConverters来解析HttpEntity中的数据，然后绑定到相应的bean上)
    public Object addPayment(@RequestBody Payment payment) {
        int result = paymentService.insertPayment(payment);
        log.info("********添加结果:" + result);
        //方法请求参数1.支持success、fail、timeout、exception 固定格式拼接,port端口号 参数2.返回前端的结果信息
        return Response.getMsg("success,port:" + serverPort, result);
    }

    @RequestMapping(value = "/getPayment/{id}")
    public Object getPaymentById(@PathVariable(value = "id") Long id) {
        Payment payment = paymentService.getPaymentById(id);
        log.info("********查询结果:" + payment.toString());
        return Response.getMsg("success,port:" + serverPort, payment);
    }

    @GetMapping(value = "/payment/discovry")
    public Object discovery() {
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("****** service:" + service);
            List<ServiceInstance> instances = discoveryClient.getInstances(service);
            for (ServiceInstance instance : instances) {
                log.info("****** ServiceId:" + instance.getServiceId() + " Host:" + instance.getHost() + " Port:" + instance.getPort() + " Uri:" + instance.getUri());
            }
        }
        return this.discoveryClient;
    }

    @GetMapping(value = "/payment/timeout")
    public String paymentFeignTimeout(){
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        return serverPort;
    }
}
