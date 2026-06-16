package com.snackshop.controller;

import com.snackshop.dto.Result;
import com.snackshop.entity.Payment;
import com.snackshop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @PostMapping("/pay")
    public Result<Payment> pay(@RequestBody Map<String, Object> params) {
        try {
            Long orderId = Long.valueOf(params.get("orderId").toString());
            Integer payType = Integer.valueOf(params.get("payType").toString());
            return Result.success(paymentService.pay(orderId, payType));
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @PostMapping("/refund/{orderId}")
    public Result<Payment> refund(@PathVariable Long orderId) {
        try {
            return Result.success(paymentService.refund(orderId));
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/order/{orderId}")
    public Result<Payment> getByOrderId(@PathVariable Long orderId) {
        Payment payment = paymentService.findByOrderId(orderId);
        return payment != null ? Result.success(payment) : Result.fail("支付记录不存在");
    }

    @GetMapping("/list")
    public Result<List<Payment>> list(@RequestParam(required = false) Long userId) {
        if (userId != null) {
            return Result.success(paymentService.findByUserId(userId));
        }
        return Result.success(paymentService.findAll());
    }
}
