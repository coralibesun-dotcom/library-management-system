package com.coraline.library.service;

import com.coraline.library.common.PageResult;
import com.coraline.library.dto.BookQueryDTO;
import com.coraline.library.entity.Book;


public interface BookService {


    /**
     * 分页查询图书
     */
    PageResult<Book> findPage(BookQueryDTO dto);



    /**
     * 查询图书详情
     */
    Book findById(Long id);


    /**
     * 新增图书
     */
    void addBook(Book book);


    /**
     * 修改图书信息
     */
    void updateBook(Book book);


    /**
     * 修改图书状态
     * 例如：上架、下架
     */
    void updateStatus(Long id, Integer status);

}
