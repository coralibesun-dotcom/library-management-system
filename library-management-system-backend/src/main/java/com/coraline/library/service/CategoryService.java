package com.coraline.library.service;

import com.coraline.library.entity.Category;

import java.util.List;

public interface CategoryService {


    // 查询所有分类
    List<Category> findAll();


    // 根据id查询分类
    Category findById(Long id);


    // 新增
    void add(Category category);


    // 修改
    void update(Category category);


    // 删除
    void delete(Long id);

}
