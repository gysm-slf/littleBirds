package core.controller;

import cn.littleBird.core.tools.Response;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.alibaba.fastjson.JSONObject;
import core.service.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
public class OrderController {

    public static final String SERVICE_URL = "http://nacos-payment-provider";

    @Resource
    private PaymentService paymentService;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/consutomer/payment/get/{id}")
    @SentinelResource(value = "fallback", fallback = "handleFallback",/*fallback只处理业务异常*/
            blockHandler = "handleblockHandler") //blockHandler只处理违背sentinel管理端规则的异常
    public Object getPayment(@PathVariable("id")Long id){
        JSONObject result = restTemplate.getForObject(SERVICE_URL + "/paymentSQL/" + id ,JSONObject.class);
        if(id == 4){
            throw new IllegalArgumentException("非法参数异常...");
        }else if(result.get("data")==null){
            throw new IllegalArgumentException("NullPointerException,该ID没有对应记录,空指针异常");
        }
        return result;
    }

    //====fallback
    public Object handleFallback(@PathVariable("id")Long id, Throwable e){
        return Response.getMsg("fail", "---非法参-------fallback的提示，数异常--", e.getMessage());
    }

    //====blockHandler                                       blockHandler的方法必须有这个参数
    public Object handleblockHandler(@PathVariable("id")Long id, BlockException e){
        return Response.getMsg("fail", "---非法参数异常--", e.getMessage());
    }




    //此处为OpenFeign方式
    @GetMapping("/consutomer/payment/get2/{id}")
    @SentinelResource(value = "fallback2", blockHandler = "handleblockHandler") //blockHandler只处理违背sentinel管理端规则的异常
    public Object getPayment2(@PathVariable("id")Long id){
        Object result = paymentService.paymentSql(id);
        if(id == 4){
            throw new IllegalArgumentException("非法参数异常...");
        }else if(result==null){
            throw new IllegalArgumentException("NullPointerException,该ID没有对应记录,空指针异常");
        }
        return result;
    }
}
