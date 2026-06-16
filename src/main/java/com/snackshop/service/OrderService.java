package com.snackshop.service;

import com.snackshop.entity.*;
import com.snackshop.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderItemRepository orderItemRepository;
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private CartService cartService;

    @Transactional
    public Orders createOrder(Long userId, String dormitory, String phone, String remark,
                              List<OrderItemRequest> items) {
        if (items == null || items.isEmpty()) {
            throw new RuntimeException("购物车为空，无法创建订单");
        }

        // 生成订单编号
        String orderNo = "O" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + ThreadLocalRandom.current().nextInt(1000, 9999);

        BigDecimal totalAmount = BigDecimal.ZERO;

        // 校验库存并计算总金额
        for (OrderItemRequest item : items) {
            Goods goods = goodsService.findById(item.getGoodsId())
                    .orElseThrow(() -> new RuntimeException("商品不存在: " + item.getGoodsId()));
            if (goods.getStatus() != 1) {
                throw new RuntimeException("商品已下架: " + goods.getName());
            }
            if (goods.getStock() < item.getQuantity()) {
                throw new RuntimeException("库存不足: " + goods.getName());
            }
            BigDecimal subtotal = goods.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            totalAmount = totalAmount.add(subtotal);
        }

        // 创建订单
        Orders order = new Orders();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setTotalAmount(totalAmount);
        order.setPayAmount(totalAmount);
        order.setDormitory(dormitory);
        order.setPhone(phone);
        order.setRemark(remark);
        order.setStatus(0); // 待支付
        order = orderRepository.save(order);

        // 创建订单明细
        for (OrderItemRequest item : items) {
            Goods goods = goodsService.findById(item.getGoodsId()).get();
            OrderItem orderItem = new OrderItem();
            orderItem.setOrderId(order.getId());
            orderItem.setGoodsId(goods.getId());
            orderItem.setGoodsName(goods.getName());
            orderItem.setGoodsImage(goods.getImage());
            orderItem.setGoodsPrice(goods.getPrice());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setSubtotal(goods.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItemRepository.save(orderItem);
        }

        return order;
    }

    @Transactional
    public Orders payOrder(Long orderId, Integer payType) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
        if (order.getStatus() != 0) {
            throw new RuntimeException("订单状态异常，无法支付");
        }

        // 扣减库存
        for (OrderItem item : order.getItems()) {
            goodsService.updateStock(item.getGoodsId(), item.getQuantity());
        }

        order.setStatus(1); // 已支付/待接单
        order.setPayType(payType);
        order.setPayTime(LocalDateTime.now());
        return orderRepository.save(order);
    }

    public Orders updateStatus(Long orderId, Integer status) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));

        if (status == 3) { // 配送中
            order.setDeliverTime(LocalDateTime.now());
        } else if (status == 4) { // 已完成
            order.setCompleteTime(LocalDateTime.now());
        } else if (status == 5) { // 已取消
            if (order.getStatus() >= 1 && order.getStatus() <= 3) {
                // 恢复库存
                for (OrderItem item : order.getItems()) {
                    goodsService.restoreStock(item.getGoodsId(), item.getQuantity());
                }
            }
        }
        order.setStatus(status);
        return orderRepository.save(order);
    }

    public Orders cancelOrder(Long orderId, String reason) {
        Orders order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("订单不存在"));
        if (order.getStatus() > 1) {
            throw new RuntimeException("订单已接单，无法取消");
        }
        order.setStatus(5);
        order.setCancelReason(reason);
        return orderRepository.save(order);
    }

    public Orders findById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public Orders findByOrderNo(String orderNo) {
        return orderRepository.findByOrderNo(orderNo).orElse(null);
    }

    public List<Orders> findByUserId(Long userId) {
        return orderRepository.findByUserIdOrderByCreateTimeDesc(userId);
    }

    public List<Orders> findByStatus(Integer status) {
        return orderRepository.findByStatusOrderByCreateTimeDesc(status);
    }

    public List<Orders> findAll() {
        return orderRepository.findAllByOrderByCreateTimeDesc();
    }

    // 内部类：订单项请求
    public static class OrderItemRequest {
        private Long goodsId;
        private Integer quantity;

        public Long getGoodsId() { return goodsId; }
        public void setGoodsId(Long goodsId) { this.goodsId = goodsId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }
}
