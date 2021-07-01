package cn.littleBird.core.service;

import cn.littleBird.core.entity.Payment;

public interface PaymentService {

    public String paymentInfo_OK(Long id);

    public String paymentInfo_TIMEOUT(Long id);

    public String paymentInfo_EXCEPTION(Long id);

    public String paymentCircuitBreaker(Integer id);
}
