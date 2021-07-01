package cn.littleBird.core.controller;

import cn.littleBird.core.entity.Payment;
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
public class OrderController {

//    public static final String PAYMENT_URL = "http://localhost:8001";
    public static final String PAYMENT_URL = "http://PAYMENT-SERVICE";

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/consumer/payment/create")
    public Object create(Payment payment) {
        return restTemplate.postForObject(PAYMENT_URL + "/addPayment/", payment, Object.class);
    }

    @GetMapping(value = "/consumer/payment/query/{id}")
    public Object queryById(@PathVariable(value = "id") Long id) {
        return restTemplate.getForObject(PAYMENT_URL + "/getPayment/" + id, Object.class);
    }

    @GetMapping(value = "/consumer/payment/query2/{id}")
    public Object getPayment(@PathVariable(value = "id") Long id){
        ResponseEntity<Object> entity = restTemplate.getForEntity(PAYMENT_URL + "/getPayment/" + id, Object.class);
        if(entity.getStatusCode().is2xxSuccessful()){
            return entity.getBody();
        }else {
            return Response.getMsg("exception",null);
        }
    }
}
