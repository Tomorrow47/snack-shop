package com.snackshop.service;

import com.snackshop.entity.AfterSale;
import com.snackshop.repository.AfterSaleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.List;

@Service
public class AfterSaleService {

    @Autowired
    private AfterSaleRepository afterSaleRepository;

    public AfterSale create(AfterSale afterSale) {
        afterSale.setStatus(0); // 待处理
        return afterSaleRepository.save(afterSale);
    }

    public AfterSale handle(Long id, String adminReply, Integer status) {
        AfterSale afterSale = afterSaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("售后记录不存在"));
        afterSale.setAdminReply(adminReply);
        afterSale.setStatus(status); // 2完成 3拒绝
        return afterSaleRepository.save(afterSale);
    }

    public AfterSale approveRefund(Long id, BigDecimal refundAmount) {
        AfterSale afterSale = afterSaleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("售后记录不存在"));
        afterSale.setRefundAmount(refundAmount);
        afterSale.setStatus(2); // 已完成
        afterSale.setAdminReply("退款已批准");
        return afterSaleRepository.save(afterSale);
    }

    public List<AfterSale> findByUserId(Long userId) {
        return afterSaleRepository.findByUserIdOrderByCreateTimeDesc(userId);
    }

    public List<AfterSale> findByStatus(Integer status) {
        return afterSaleRepository.findByStatusOrderByCreateTimeDesc(status);
    }

    public List<AfterSale> findAll() {
        return afterSaleRepository.findAllByOrderByCreateTimeDesc();
    }

    public AfterSale findById(Long id) {
        return afterSaleRepository.findById(id).orElse(null);
    }
}
