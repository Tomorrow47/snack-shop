package com.snackshop.controller;

import com.snackshop.dto.Result;
import com.snackshop.entity.Category;
import com.snackshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/list")
    public Result<List<Category>> list() {
        return Result.success(categoryService.findAll());
    }

    @GetMapping("/all")
    public Result<List<Category>> all() {
        return Result.success(categoryService.findAllWithDisabled());
    }

    @GetMapping("/{id}")
    public Result<Category> getById(@PathVariable Long id) {
        return categoryService.findById(id)
                .map(Result::success)
                .orElse(Result.fail("分类不存在"));
    }

    @GetMapping("/search")
    public Result<List<Category>> search(@RequestParam String keyword) {
        return Result.success(categoryService.search(keyword));
    }

    @PostMapping
    public Result<Category> save(@RequestBody Category category) {
        return Result.success(categoryService.save(category));
    }

    @PutMapping("/{id}")
    public Result<Category> update(@PathVariable Long id, @RequestBody Category category) {
        return Result.success(categoryService.update(id, category));
    }

    @DeleteMapping("/{id}")
    public Result<Void> delete(@PathVariable Long id) {
        categoryService.delete(id);
        return Result.success();
    }
}
