package com.coraline.library.dto;


import lombok.Data;

import java.time.LocalDateTime;


@Data
public class OperationLogQueryDTO {


    // 根据用户查询
    private Long userId;


    // 根据操作类型查询
    private String operation;


    // 开始时间
    private LocalDateTime startTime;


    // 结束时间
    private LocalDateTime endTime;


    // 分页开始位置
    private Integer offset;


    // 每页数量
    private Integer pageSize;

}
