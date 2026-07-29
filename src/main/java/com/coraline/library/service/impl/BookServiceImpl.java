package com.coraline.library.service.impl;

import com.coraline.library.common.PageResult;
import com.coraline.library.common.annotation.Log;
import com.coraline.library.common.enums.BookStatusEnum;
import com.coraline.library.common.enums.ResultCodeEnum;
import com.coraline.library.dto.BookQueryDTO;
import com.coraline.library.entity.Book;
import com.coraline.library.exception.BusinessException;
import com.coraline.library.mapper.BookMapper;
import com.coraline.library.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BookServiceImpl implements BookService {
    private final BookMapper bookMapper;


    public BookServiceImpl(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }

    @Override
    public PageResult<Book> findPage(BookQueryDTO dto) {


        if(dto == null){
            dto = new BookQueryDTO();
        }

        if(dto.getPageNum() == null){
            dto.setPageNum(1);
        }

        if(dto.getPageSize() == null){
            dto.setPageSize(10);
        }


        if(dto.getKeyword() != null){
            dto.setKeyword(dto.getKeyword().trim());
        }


        Integer offset =
                (dto.getPageNum() - 1) * dto.getPageSize();


        List<Book> books =
                bookMapper.findPage(dto, offset, dto.getPageSize());


        Long total =
                bookMapper.count(dto);


        return new PageResult<>(books,total);

    }


    @Override
    public Book findById(Long id) {

        return bookMapper.findById(id);
    }

    @Log("新增图书")
    @Override
    public void addBook(Book book){

        book.setStatus(BookStatusEnum.DRAFT.getCode());

        bookMapper.insert(book);

    }

    @Log("修改图书")
    @Override
    public void updateBook(Book book) {
        bookMapper.update(book);
    }

    @Log("修改图书状态")
    @Override
    public void updateStatus(Long id, Integer status) {

        if(status == null){
            throw new BusinessException(
                    ResultCodeEnum.PARAM_ERROR,
                    "状态不能为空"
            );
        }

        boolean valid =
                status.equals(BookStatusEnum.DRAFT.getCode())
                        || status.equals(BookStatusEnum.ON_SALE.getCode())
                        || status.equals(BookStatusEnum.OFF_SALE.getCode());


        if(!valid){
            throw new BusinessException(
                    ResultCodeEnum.BOOK_STATUS_ERROR,
                    "图书状态错误"
            );
        }


        bookMapper.updateStatus(id,status);

    }
}
