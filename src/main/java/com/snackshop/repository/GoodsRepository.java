package com.snackshop.repository;

import com.snackshop.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Long> {
    List<Goods> findByStatusOrderByCreateTimeDesc(Integer status);
    List<Goods> findByCategoryIdAndStatus(Long categoryId, Integer status);
    List<Goods> findByNameContainingAndStatus(String keyword, Integer status);
    List<Goods> findByIsRecommendAndStatus(Integer isRecommend, Integer status);
    List<Goods> findByIsHotAndStatus(Integer isHot, Integer status);

    @Query("SELECT g FROM Goods g WHERE g.status = 1 ORDER BY g.sales DESC")
    List<Goods> findTopSales();

    @Query("SELECT g FROM Goods g WHERE g.stock <= g.stockWarning AND g.status = 1")
    List<Goods> findLowStock();
}
