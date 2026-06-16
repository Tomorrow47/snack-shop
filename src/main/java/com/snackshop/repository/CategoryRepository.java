package com.snackshop.repository;

import com.snackshop.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByStatusOrderBySortOrderAsc(Integer status);
    List<Category> findByNameContainingAndStatus(String name, Integer status);
}
