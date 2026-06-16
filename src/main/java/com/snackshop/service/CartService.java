package com.snackshop.service;

import com.snackshop.entity.Cart;
import com.snackshop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    public List<Cart> findByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public Cart addToCart(Long userId, Long goodsId, int quantity) {
        Optional<Cart> existing = cartRepository.findByUserIdAndGoodsId(userId, goodsId);
        if (existing.isPresent()) {
            Cart cart = existing.get();
            cart.setQuantity(cart.getQuantity() + quantity);
            return cartRepository.save(cart);
        } else {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setGoodsId(goodsId);
            cart.setQuantity(quantity);
            return cartRepository.save(cart);
        }
    }

    public Cart updateQuantity(Long cartId, int quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("购物车记录不存在"));
        if (quantity <= 0) {
            cartRepository.delete(cart);
            return null;
        }
        cart.setQuantity(quantity);
        return cartRepository.save(cart);
    }

    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void clearCart(Long userId) {
        cartRepository.deleteByUserId(userId);
    }
}
