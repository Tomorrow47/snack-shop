package com.snackshop.controller;

import com.snackshop.dto.Result;
import com.snackshop.entity.Orders;
import com.snackshop.repository.OrderRepository;
import com.snackshop.repository.OrderItemRepository;
import com.snackshop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @PostMapping("/create")
    public Result<Orders> create(@RequestBody Map<String, Object> params) {
        try {
            Long userId = Long.valueOf(params.get("userId").toString());
            String dormitory = params.get("dormitory").toString();
            String phone = params.get("phone").toString();
            String remark = params.get("remark") != null ? params.get("remark").toString() : "";

            List<Map<String, Object>> itemsList = (List<Map<String, Object>>) params.get("items");
            List<OrderService.OrderItemRequest> items = new ArrayList<>();
            for (Map<String, Object> item : itemsList) {
                OrderService.OrderItemRequest req = new OrderService.OrderItemRequest();
                req.setGoodsId(Long.valueOf(item.get("goodsId").toString()));
                req.setQuantity(Integer.valueOf(item.get("quantity").toString()));
                items.add(req);
            }

            Orders order = orderService.createOrder(userId, dormitory, phone, remark, items);
            return Result.success(order);
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<Orders>> list(@RequestParam(required = false) Long userId,
                                     @RequestParam(required = false) Integer status) {
        if (userId != null) {
            return Result.success(orderService.findByUserId(userId));
        }
        if (status != null) {
            return Result.success(orderService.findByStatus(status));
        }
        return Result.success(orderService.findAll());
    }

    @GetMapping("/{id}")
    public Result<Orders> getById(@PathVariable Long id) {
        Orders order = orderService.findById(id);
        return order != null ? Result.success(order) : Result.fail("订单不存在");
    }

    @GetMapping("/no/{orderNo}")
    public Result<Orders> getByOrderNo(@PathVariable String orderNo) {
        Orders order = orderService.findByOrderNo(orderNo);
        return order != null ? Result.success(order) : Result.fail("订单不存在");
    }

    @PutMapping("/{id}/status")
    public Result<Orders> updateStatus(@PathVariable Long id, @RequestBody Map<String, Integer> params) {
        try {
            Integer status = params.get("status");
            return Result.success(orderService.updateStatus(id, status));
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @PutMapping("/{id}/cancel")
    public Result<Orders> cancel(@PathVariable Long id, @RequestBody Map<String, String> params) {
        try {
            String reason = params.getOrDefault("reason", "用户主动取消");
            return Result.success(orderService.cancelOrder(id, reason));
        } catch (RuntimeException e) {
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/dashboard")
    public Result<Map<String, Object>> dashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("totalOrders", orderRepository.count());
        data.put("completedOrders", orderRepository.countCompleted());
        data.put("todayOrders", orderRepository.countTodayOrders());
        data.put("revenue", orderRepository.sumRevenue());
        data.put("topSelling", orderItemRepository.findTopSellingGoods());
        return Result.success(data);
    }
}
