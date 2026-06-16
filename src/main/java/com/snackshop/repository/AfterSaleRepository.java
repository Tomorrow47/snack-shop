package com.snackshop.repository;

import com.snackshop.entity.AfterSale;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AfterSaleRepository extends JpaRepository<AfterSale, Long> {
    List<AfterSale> findByUserIdOrderByCreateTimeDesc(Long userId);
    List<AfterSale> findByStatusOrderByCreateTimeDesc(Integer status);
    List<AfterSale> findByOrderId(Long orderId);
    List<AfterSale> findAllByOrderByCreateTimeDesc();
}
