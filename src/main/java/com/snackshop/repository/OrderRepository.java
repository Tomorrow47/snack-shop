package com.snackshop.repository;

import com.snackshop.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    Optional<Orders> findByOrderNo(String orderNo);
    List<Orders> findByUserIdOrderByCreateTimeDesc(Long userId);
    List<Orders> findByStatusOrderByCreateTimeDesc(Integer status);
    List<Orders> findAllByOrderByCreateTimeDesc();

    @Query("SELECT o FROM Orders o WHERE o.userId = :userId AND o.status = :status ORDER BY o.createTime DESC")
    List<Orders> findByUserIdAndStatus(Long userId, Integer status);

    @Query("SELECT COUNT(o) FROM Orders o WHERE o.status = 4")
    long countCompleted();

    @Query("SELECT COALESCE(SUM(o.payAmount), 0) FROM Orders o WHERE o.status >= 1 AND o.status <= 4")
    java.math.BigDecimal sumRevenue();

    @Query("SELECT COUNT(o) FROM Orders o WHERE FUNCTION('DATE', o.createTime) = CURRENT_DATE")
    long countTodayOrders();
}
