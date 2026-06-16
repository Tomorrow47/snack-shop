package com.snackshop.controller;

import com.snackshop.dto.Result;
import com.snackshop.entity.Goods;
import com.snackshop.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping("/list")
    public Result<List<Goods>> list(@RequestParam(required = false) Long categoryId) {
        if (categoryId != null) {
            return Result.success(goodsService.findByCategory(categoryId));
        }
        return Result.success(goodsService.findAll());
    }

    @GetMapping("/{id}")
    public Result<Goods> getById(@PathVariable Long id) {
        return goodsService.findById(id)
                .map(Result::success)
                .orElse(Result.fail("商品不存在"));
    }

    @GetMapping("/search")
    public Result<List<Goods>> search(@RequestParam String keyword) {
        return Result.success(goodsService.search(keyword));
    }

    @GetMapping("/recommend")
    public Result<List<Goods>> recommend() {
        return Result.success(goodsService.findRecommend());
    }

    @GetMapping("/hot")
    public Result<List<Goods>> hot() {
        return Result.success(goodsService.findHot());
    }

    @GetMapping("/top-sales")
    public Result<List<Goods>> topSales() {
        return Result.success(goodsService.findTopSales());
    }

    @GetMapping("/low-stock")
    public Result<List<Goods>> lowStock() {
        return Result.success(goodsService.findLowStock());
    }

    @PostMapping
    public Result<Goods> save(@RequestBody Goods goods) {
        return Result.success(goodsService.save(goods));
    }

    @PutMapping("/{id}")
    public Result<Goods> update(@PathVariable Long id, @RequestBody Goods goods) {
        return Result.success(goodsService.update(id, goods));
    }

    @PutMapping("/{id}/toggle")
    public Result<Void> toggleStatus(@PathVariable Long id) {
        goodsService.toggleStatus(id);
        return Result.success();
    }
}
