package com.snackshop.repository;

import com.snackshop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    List<Cart> findByUserId(Long userId);
    Optional<Cart> findByUserIdAndGoodsId(Long userId, Long goodsId);
    void deleteByUserId(Long userId);
    void deleteByUserIdAndGoodsId(Long userId, Long goodsId);
}
