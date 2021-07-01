//package cn.littleBird.core.service;
//
//import org.springframework.stereotype.Component;
//
////可以把兜底方法抽取一个单独的类到这里，然后service层注解改为@FeignClient(value = "HYSTRIX-PAYMENT-SERVICE",fallback = PaymentFallbackService.class)
//@Component
//public class PaymentFallbackService implements PaymentService{
//    @Override
//    public Object getPaymentOK(Long id) {
//        return "getPaymentOK ,请求异常，请稍后再试";
//    }
//
//    @Override
//    public Object getPaymentTIMEOUT(Long id) {
//        return "getPaymentTIMEOUT ,请求异常，请稍后再试";
//    }
//}
