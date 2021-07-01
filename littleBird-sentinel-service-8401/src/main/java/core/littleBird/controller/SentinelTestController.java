package core.littleBird.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
public class SentinelTestController {

    private int i = 0;

    @GetMapping("/testA")
    public String testA(){
        int n = 10 / 0;
        return "testA----";
    }

    @GetMapping("/testB")
    public String testB() throws InterruptedException {
        System.out.println("now:"+i++);
        System.out.println(Thread.currentThread().getName());
        TimeUnit.MILLISECONDS.sleep(3000);
        return "testB*****";
    }

    @GetMapping("/testhotkey")
    @SentinelResource(value = "testhotkey", blockHandler = "deal_testhotkey")
    //这个value是随意的值，并不和请求路径必须一致
    //在填写热点限流的 资源名 这一项时，可以填 /testhotkey 或者是 @SentinelResource的value的值
    public String testHotKey(
            @RequestParam(value="p1", required = false) String p1,
            @RequestParam(value = "p2", required = false) String p2
    ){
        return "testHotKey__success";
    }

    public String deal_testhotkey(String p1, String p2, BlockException e){
        return "testhotkey__fail";
    }
}
