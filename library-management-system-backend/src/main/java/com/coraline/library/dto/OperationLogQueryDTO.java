package com.coraline.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OperationLogQueryDTO {

    /**
     * 当前页
     */
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;


    /**
     * 每页数量
     */
    @Min(value = 1, message = "每页数量必须大于0")
    @Max(value = 20, message = "每页最多20条")
    private Integer pageSize = 10;


    /**
     * 用户id
     */
    private Long userId;


    /**
     * 操作类型
     */
    private String operation;


    /**
     * 开始时间
     */
    private LocalDateTime startTime;


    /**
     * 结束时间
     */
    private LocalDateTime endTime;

}
