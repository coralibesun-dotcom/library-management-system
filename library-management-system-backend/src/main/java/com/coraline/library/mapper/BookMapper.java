package com.coraline.library.mapper;

import com.coraline.library.dto.BookQueryDTO;
import com.coraline.library.entity.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
@Mapper
public interface BookMapper {


    // 分页查询全部---以List形式返回,List里以实体形式返回,n个book
    List<Book> findPage(
            @Param("dto") BookQueryDTO dto,//分类筛选,关键词搜索
            @Param("offset") Integer offset,
            @Param("pageSize") Integer pageSize
    );

    // 查询图书总数量<分页>
    Long count(@Param("dto") BookQueryDTO dto);

    // 按id查询图书总数量
    Integer countByCategoryId(Long categoryId);


    // 查看详情
    Book findById(Long id);


    // 新增
    int insert(Book book);


    // 修改
    int update(Book book);


    // 单个下架
    int updateStatus(Long id,Integer status);


    // 批量下架
    int batchUpdateStatus(List<Long> ids, Integer status);


    // 借书库存减少
    int decreaseStock(Long id);


    // 还书库存增加
    int increaseStock(Long id);

}
