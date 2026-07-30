package com.coraline.library.dto;


import lombok.Data;

import java.time.LocalDateTime;


@Data
public class OperationLogQueryDTO {


    /**
     * 当前页
     */
    private Integer pageNum;


    /**
     * 每页数量
     */
    private Integer pageSize;



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
