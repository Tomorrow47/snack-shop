package com.snackshop.controller;

import com.snackshop.dto.Result;
import com.snackshop.entity.AfterSale;
import com.snackshop.service.AfterSaleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/after-sale")
public class AfterSaleController {

    @Autowired
    private AfterSaleService afterSaleService;

    @PostMapping
    public Result<AfterSale> create(@RequestBody AfterSale afterSale) {
        return Result.success(afterSaleService.create(afterSale));
    }

    @GetMapping("/list")
    public Result<List<AfterSale>> list(@RequestParam(required = false) Long userId,
                                        @RequestParam(required = false) Integer status) {
        if (userId != null) {
            return Result.success(afterSaleService.findByUserId(userId));
        }
        if (status != null) {
            return Result.success(afterSaleService.findByStatus(status));
        }
        return Result.success(afterSaleService.findAll());
    }

    @GetMapping("/{id}")
    public Result<AfterSale> getById(@PathVariable Long id) {
        AfterSale afterSale = afterSaleService.findById(id);
        return afterSale != null ? Result.success(afterSale) : Result.fail("售后记录不存在");
    }

    @PutMapping("/{id}/handle")
    public Result<AfterSale> handle(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        String reply = params.get("adminReply") != null ? params.get("adminReply").toString() : "";
        Integer status = Integer.valueOf(params.get("status").toString());
        return Result.success(afterSaleService.handle(id, reply, status));
    }

    @PutMapping("/{id}/refund")
    public Result<AfterSale> approveRefund(@PathVariable Long id, @RequestBody Map<String, Object> params) {
        BigDecimal amount = new BigDecimal(params.get("refundAmount").toString());
        return Result.success(afterSaleService.approveRefund(id, amount));
    }
}
