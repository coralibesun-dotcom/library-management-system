package com.coraline.library.controller;


import com.coraline.library.common.PageResult;
import com.coraline.library.common.Result;
import com.coraline.library.common.annotation.RequireRole;
import com.coraline.library.dto.BorrowDTO;
import com.coraline.library.dto.BorrowQueryDTO;
import com.coraline.library.entity.BorrowRecord;
import com.coraline.library.service.BorrowRecordService;
import com.coraline.library.vo.BorrowRecordVO;
import org.springframework.validation.annotation.Validated;
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


    /**
     * 分页查询借阅记录
     * 管理员：查所有人的记录；普通用户：只查自己的（Service 层强制收敛）
     * 示例：/borrow/page?pageNum=1&pageSize=10&status=0
     */
    @RequireRole({"USER","ADMIN"})
    @GetMapping("/page")
    public Result<PageResult<BorrowRecordVO>> findPage(
            @Validated BorrowQueryDTO dto
    ){

        return Result.success(
                borrowRecordService.findPage(dto)
        );

    }
}
