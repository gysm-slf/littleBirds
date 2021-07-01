package cn.littleBird.core.service.impl;

import cn.littleBird.core.dao.PaymentMapper;
import cn.littleBird.core.entity.Payment;
import cn.littleBird.core.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentMapper paymentMapper;

    @Override
    public Payment getPaymentById(Long id) {
        return paymentMapper.selectPaymentById(id);
    }

    @Override
    public int insertPayment(Payment payment) {
        return paymentMapper.insertPayment(payment);
    }
}
