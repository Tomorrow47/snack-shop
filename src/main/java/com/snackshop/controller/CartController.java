package com.snackshop.controller;

import com.snackshop.dto.Result;
import com.snackshop.entity.Cart;
import com.snackshop.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/list/{userId}")
    public Result<List<Cart>> list(@PathVariable Long userId) {
        return Result.success(cartService.findByUserId(userId));
    }

    @PostMapping("/add")
    public Result<Cart> add(@RequestBody Map<String, Object> params) {
        Long userId = Long.valueOf(params.get("userId").toString());
        Long goodsId = Long.valueOf(params.get("goodsId").toString());
        int quantity = params.get("quantity") != null ? Integer.parseInt(params.get("quantity").toString()) : 1;
        return Result.success(cartService.addToCart(userId, goodsId, quantity));
    }

    @PutMapping("/{cartId}")
    public Result<Cart> updateQuantity(@PathVariable Long cartId, @RequestBody Map<String, Integer> params) {
        int quantity = params.get("quantity");
        Cart cart = cartService.updateQuantity(cartId, quantity);
        return Result.success(cart);
    }

    @DeleteMapping("/{cartId}")
    public Result<Void> remove(@PathVariable Long cartId) {
        cartService.removeFromCart(cartId);
        return Result.success();
    }

    @DeleteMapping("/clear/{userId}")
    public Result<Void> clear(@PathVariable Long userId) {
        cartService.clearCart(userId);
        return Result.success();
    }
}
