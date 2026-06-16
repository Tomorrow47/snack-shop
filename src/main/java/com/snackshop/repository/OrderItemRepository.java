package com.snackshop.repository;

import com.snackshop.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrderId(Long orderId);

    @Query("SELECT oi.goodsId, SUM(oi.quantity) FROM OrderItem oi GROUP BY oi.goodsId ORDER BY SUM(oi.quantity) DESC")
    List<Object[]> findTopSellingGoods();
}
