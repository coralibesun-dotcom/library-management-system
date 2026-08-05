package com.coraline.library.controller;


import com.coraline.library.common.Result;
import com.coraline.library.common.annotation.RequireRole;
import com.coraline.library.dto.BorrowDTO;
import com.coraline.library.entity.BorrowRecord;
import com.coraline.library.service.BorrowRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/borrow")
public class BorrowRecordController {


    private final BorrowRecordService borrowRecordService;


    public BorrowRecordController(
            BorrowRecordService borrowRecordService
    ){

        this.borrowRecordService = borrowRecordService;

    }



    /**
     * 借阅图书
     */
    @RequireRole({"USER","ADMIN"})
    @PostMapping
    public Result<Void> borrowBook(
            @RequestBody BorrowDTO dto
    ){

        borrowRecordService.borrowBook(
                dto
        );


        return Result.success();

    }




    /**
     * 归还图书
     */
    @RequireRole({"USER","ADMIN"})
    @PutMapping("/return/{id}")
    public Result<Void> returnBook(
            @PathVariable Long id
    ){

        borrowRecordService.returnBook(id);


        return Result.success();

    }




    /**
     * 查询用户借阅记录
     */
    @GetMapping("/my")
    @RequireRole({"USER","ADMIN"})
    public Result<List<BorrowRecord>> findMyBorrow(){

        return Result.success(
                borrowRecordService.findMyBorrow()
        );

    }

    /**
     * 全部借阅记录
     */
    @RequireRole("ADMIN")
    @GetMapping("/all")
    public Result<List<BorrowRecord>> findAll(){

        return Result.success(
                borrowRecordService.findAll()
        );

    }
}
