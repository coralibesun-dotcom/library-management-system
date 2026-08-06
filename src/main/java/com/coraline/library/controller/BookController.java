package com.coraline.library.controller;


import com.coraline.library.common.PageResult;
import com.coraline.library.common.Result;
import com.coraline.library.common.annotation.RequireRole;
import com.coraline.library.dto.BookQueryDTO;
import com.coraline.library.entity.Book;
import com.coraline.library.service.BookService;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/book")
public class BookController {


    private final BookService bookService;


    public BookController(BookService bookService){

        this.bookService = bookService;

    }



    /**
     * 分页查询图书
     */
    @RequireRole({"GUEST","USER","ADMIN"})
    @GetMapping("/page")
    public Result<PageResult<Book>> findPage(
            BookQueryDTO dto
    ){

        return Result.success(
                bookService.findPage(dto)
        );

    }



    /**
     * 根据id查询图书详情
     */
    @RequireRole({"GUEST","USER","ADMIN"})
    @GetMapping("/{id}")
    public Result<Book> findById(
            @PathVariable Long id
    ){

        return Result.success(
                bookService.findById(id)
        );

    }



    /**
     * 新增图书
     */
    @RequireRole("ADMIN")
    @PostMapping
    public Result<Void> addBook(
            @RequestBody Book book
    ){

        bookService.addBook(book);

        return Result.success();

    }



    /**
     * 修改图书
     */
    @RequireRole("ADMIN")
    @PutMapping
    public Result<Void> updateBook(
            @RequestBody Book book
    ){

        bookService.updateBook(book);

        return Result.success();

    }



    /**
     * 修改图书状态
     */
    @RequireRole("ADMIN")
    @PutMapping("/status")
    public Result<Void> updateStatus(
            @RequestParam Long id,
            @RequestParam Integer status
    ){

        bookService.updateStatus(id,status);

        return Result.success();

    }

}
