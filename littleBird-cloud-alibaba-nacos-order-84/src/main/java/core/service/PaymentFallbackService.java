package core.service;

import cn.littleBird.core.tools.Response;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService{
//想一下怎么设置全局的fallback还有如何设置blockhandler
    @Override
    public Object paymentSql(Long id) {
        return Response.getMsg("exception","服务降级返回");
    }
}
