package com.coraline.library.controller;

import com.coraline.library.common.Result;
import com.coraline.library.common.annotation.RequireRole;
import com.coraline.library.entity.Category;
import com.coraline.library.service.CategoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/findAll")
    public Result<List<Category>> findAll() {
        return Result.success(
                categoryService.findAll()
        );
    }

    @GetMapping("/{id}")
    public Result<Category> findById( @PathVariable Long id) {
        return Result.success(
                categoryService.findById(id)
        );
    }

    /**
     * 新增分类
     */
    @RequireRole("ADMIN")
    @PostMapping
    public Result<Void> add(
            @RequestBody Category category
    ){

        categoryService.add(category);

        return Result.success();

    }



    /**
     * 修改分类
     */
    @RequireRole("ADMIN")
    @PutMapping
    public Result<Void> update(
            @RequestBody Category category
    ){

        categoryService.update(category);

        return Result.success();

    }



    /**
     * 删除分类
     */
    @RequireRole("ADMIN")
    @DeleteMapping("/{id}")
    public Result<Void> delete(
            @PathVariable Long id
    ){

        categoryService.delete(id);

        return Result.success();

    }
}
