package com.snackshop.service;

import com.snackshop.entity.Goods;
import com.snackshop.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class GoodsService {

    @Autowired
    private GoodsRepository goodsRepository;

    public List<Goods> findAll() {
        return goodsRepository.findByStatusOrderByCreateTimeDesc(1);
    }

    public List<Goods> findByCategory(Long categoryId) {
        return goodsRepository.findByCategoryIdAndStatus(categoryId, 1);
    }

    public List<Goods> search(String keyword) {
        return goodsRepository.findByNameContainingAndStatus(keyword, 1);
    }

    public List<Goods> findRecommend() {
        return goodsRepository.findByIsRecommendAndStatus(1, 1);
    }

    public List<Goods> findHot() {
        return goodsRepository.findByIsHotAndStatus(1, 1);
    }

    public List<Goods> findTopSales() {
        return goodsRepository.findTopSales();
    }

    public List<Goods> findLowStock() {
        return goodsRepository.findLowStock();
    }

    public Optional<Goods> findById(Long id) {
        return goodsRepository.findById(id);
    }

    public Goods save(Goods goods) {
        return goodsRepository.save(goods);
    }

    public Goods update(Long id, Goods goods) {
        Goods existing = goodsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
        existing.setName(goods.getName());
        existing.setCategoryId(goods.getCategoryId());
        existing.setPrice(goods.getPrice());
        existing.setOriginalPrice(goods.getOriginalPrice());
        existing.setStock(goods.getStock());
        existing.setStockWarning(goods.getStockWarning());
        existing.setImage(goods.getImage());
        existing.setDescription(goods.getDescription());
        existing.setIsRecommend(goods.getIsRecommend());
        existing.setIsHot(goods.getIsHot());
        existing.setStatus(goods.getStatus());
        return goodsRepository.save(existing);
    }

    public void updateStock(Long goodsId, int quantity) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
        if (goods.getStock() < quantity) {
            throw new RuntimeException("库存不足");
        }
        goods.setStock(goods.getStock() - quantity);
        goods.setSales(goods.getSales() + quantity);
        goodsRepository.save(goods);
    }

    public void restoreStock(Long goodsId, int quantity) {
        Goods goods = goodsRepository.findById(goodsId)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
        goods.setStock(goods.getStock() + quantity);
        goods.setSales(Math.max(0, goods.getSales() - quantity));
        goodsRepository.save(goods);
    }

    public void toggleStatus(Long id) {
        Goods goods = goodsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("商品不存在"));
        goods.setStatus(goods.getStatus() == 1 ? 0 : 1);
        goodsRepository.save(goods);
    }
}
