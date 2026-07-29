package com.coraline.library.controller;


import com.coraline.library.common.Result;
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
    @GetMapping("/user/{userId}")
    public Result<List<BorrowRecord>> findByUserId(
            @PathVariable Long userId
    ){

        return Result.success(
                borrowRecordService.findByUserId(userId)
        );

    }

}
