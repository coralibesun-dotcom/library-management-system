package com.coraline.library.service.impl;


import com.coraline.library.common.enums.ResultCodeEnum;
import com.coraline.library.entity.Category;
import com.coraline.library.exception.BusinessException;
import com.coraline.library.mapper.BookMapper;
import com.coraline.library.mapper.CategoryMapper;
import com.coraline.library.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CategoryServiceImpl implements CategoryService {


    private final CategoryMapper categoryMapper;

    public CategoryServiceImpl(CategoryMapper categoryMapper, BookMapper bookMapper) {
        this.categoryMapper = categoryMapper;
        this.bookMapper = bookMapper;
    }

    private final BookMapper bookMapper;






    /**
     * 查询所有分类
     */
    @Override
    public List<Category> findAll() {


        return categoryMapper.findAll();

    }



    /**
     * 根据id查询分类
     */
    @Override
    public Category findById(Long id) {


        Category category = categoryMapper.findById(id);


        if(category == null){

            throw new BusinessException(
                    ResultCodeEnum.CATEGORY_NOT_FOUND,
                    "分类不存在"
            );

        }


        return category;

    }

    @Override
    public void add(Category category) {

        categoryMapper.insert(category);

    }


    @Override
    public void update(Category category) {

        categoryMapper.update(category);

    }


    @Override
    public void delete(Long id) {
        Integer count = bookMapper.countByCategoryId(id);

        if(count > 0){
            throw new BusinessException(ResultCodeEnum.CATEGORY_HAS_BOOK,"该分类下存在图书，无法删除");
        }

        categoryMapper.delete(id);

    }

}
