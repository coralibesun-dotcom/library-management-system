package com.coraline.library.controller;


import com.coraline.library.common.PageResult;
import com.coraline.library.common.Result;
import com.coraline.library.common.annotation.RequireRole;
import com.coraline.library.dto.OperationLogQueryDTO;
import com.coraline.library.entity.OperationLog;
import com.coraline.library.service.OperationLogService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/log")
public class OperationLogController {


    private final OperationLogService operationLogService;


    public OperationLogController(OperationLogService operationLogService) {

        this.operationLogService = operationLogService;

    }


    /**
     * 分页查询操作日志
     */
    @RequireRole("ADMIN")
    @GetMapping("/page")
    public Result<PageResult<OperationLog>> findPage(
            @Validated OperationLogQueryDTO query
    ){

        return Result.success(
                operationLogService.findPage(query)
        );

    }

}
