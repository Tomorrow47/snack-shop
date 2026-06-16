package com.snackshop.service;

import com.snackshop.entity.Payment;
import com.snackshop.entity.Orders;
import com.snackshop.repository.PaymentRepository;
import com.snackshop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    /**
     * 发起支付（模拟电子支付）
     */
    @Transactional
    public Payment pay(Long orderId, Integer payType) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
        if (order.getStatus() != 0) {
            throw new RuntimeException("订单状态异常，无法发起支付");
        }

        // 检查是否已有支付记录
        Optional<Payment> existing = paymentRepository.findByOrderId(orderId);
        if (existing.isPresent() && existing.get().getPayStatus() == 1) {
            throw new RuntimeException("订单已支付");
        }

        // 创建支付记录
        Payment payment = new Payment();
        payment.setOrderId(orderId);
        payment.setOrderNo(order.getOrderNo());
        payment.setUserId(order.getUserId());
        payment.setAmount(order.getPayAmount());
        payment.setPayType(payType);
        payment.setPayStatus(0); // 待支付

        // 模拟支付成功：生成第三方交易号
        String prefix = payType == 1 ? "WX" : "ZFB";
        String txNo = prefix + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + ThreadLocalRandom.current().nextInt(100000, 999999);
        payment.setTransactionNo(txNo);
        payment.setPayStatus(1); // 支付成功
        payment.setPayTime(LocalDateTime.now());
        payment = paymentRepository.save(payment);

        // 更新订单状态
        orderService.payOrder(orderId, payType);

        return payment;
    }

    /**
     * 退款
     */
    @Transactional
    public Payment refund(Long orderId) {
        Payment payment = paymentRepository.findByOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("支付记录不存在"));
        if (payment.getPayStatus() != 1) {
            throw new RuntimeException("支付状态异常，无法退款");
        }

        payment.setRefundAmount(payment.getAmount());
        payment.setRefundTime(LocalDateTime.now());
        payment.setPayStatus(3); // 已退款
        payment = paymentRepository.save(payment);

        // 更新订单状态为已退款
        orderService.updateStatus(orderId, 6);

        return payment;
    }

    public Payment findByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId).orElse(null);
    }

    public Payment findByOrderNo(String orderNo) {
        return paymentRepository.findByOrderNo(orderNo).orElse(null);
    }

    public List<Payment> findByUserId(Long userId) {
        return paymentRepository.findByUserId(userId);
    }

    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }
}
