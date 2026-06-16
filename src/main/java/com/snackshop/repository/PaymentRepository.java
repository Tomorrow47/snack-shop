package com.snackshop.repository;

import com.snackshop.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(Long orderId);
    Optional<Payment> findByOrderNo(String orderNo);
    Optional<Payment> findByTransactionNo(String transactionNo);
    List<Payment> findByUserId(Long userId);
}
