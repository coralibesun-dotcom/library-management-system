package com.coraline.library.mapper;

import com.coraline.library.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {

    // 查询所有分类--图书筛选时加载分类列表
    List<Category> findAll();

    // 根据id查询分类
    Category findById(Long id);

    void insert(Category category);


    void update(Category category);


    void delete(Long id);

}
