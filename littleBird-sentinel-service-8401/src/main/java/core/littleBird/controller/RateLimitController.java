package core.littleBird.controller;

import cn.littleBird.core.entity.Payment;
import cn.littleBird.core.tools.Response;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import core.littleBird.myhandler.littleBirdBlockHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RateLimitController {

    @GetMapping("/byResource")
    @SentinelResource(value = "byResource", blockHandler = "handleException")
    public Object byResource(){
        return Response.getMsg("success", "按照资源名限流测试0K", new Payment(2020L,"serial001"));
    }

    public Object handleException(BlockException e){
        return Response.getMsg("exception", e.getClass().getCanonicalName() + "\t 服务不可用");
    }

    @GetMapping("/littleBirdBlockHandler")
    @SentinelResource(value = "littleBirdBlockHandler",
            blockHandlerClass = littleBirdBlockHandler.class,  //兜底处理类
            blockHandler = "handlerException2")  //兜底处理类里面的方法  (必须为static静态方法)
    public Object customerRequest(){
        return Response.getMsg("success", "嘎嘎嘎自定义----success", new Payment(2020L,"serial001"));
    }

}
