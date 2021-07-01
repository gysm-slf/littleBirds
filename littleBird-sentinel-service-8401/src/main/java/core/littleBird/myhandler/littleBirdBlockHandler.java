package core.littleBird.myhandler;

import cn.littleBird.core.entity.Payment;
import cn.littleBird.core.tools.Response;
import com.alibaba.csp.sentinel.slots.block.BlockException;

public class littleBirdBlockHandler {

    public static Object handlerException(BlockException e){
        return Response.getMsg("success",  new Payment(2525L, "handlerException1"));
    }

    public static Object handlerException2(BlockException e){
        return Response.getMsg("success",  new Payment(2525L, "handlerException2"));
    }
}
