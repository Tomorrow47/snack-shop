package com.snackshop.service;

import com.snackshop.entity.Category;
import com.snackshop.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> findAll() {
        return categoryRepository.findByStatusOrderBySortOrderAsc(1);
    }

    public List<Category> findAllWithDisabled() {
        return categoryRepository.findAll();
    }

    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> search(String keyword) {
        return categoryRepository.findByNameContainingAndStatus(keyword, 1);
    }

    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    public Category update(Long id, Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("分类不存在"));
        existing.setName(category.getName());
        existing.setSortOrder(category.getSortOrder());
        existing.setStatus(category.getStatus());
        return categoryRepository.save(existing);
    }

    public void delete(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("分类不存在"));
        category.setStatus(0);
        categoryRepository.save(category);
    }
}
