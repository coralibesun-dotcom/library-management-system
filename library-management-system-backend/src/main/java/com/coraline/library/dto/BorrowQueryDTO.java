package com.coraline.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class BorrowQueryDTO {

    /**
     * 当前页
     */
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;

    /**
     * 每页数量
     */
    @Min(value = 1, message = "每页数量必须大于0")
    @Max(value = 20, message = "每页最多查询20条")
    private Integer pageSize = 10;

    /**
     * 状态筛选：0借阅中 1已归还 2逾期（不传 = 查全部状态）
     */
    private Integer status;

    /**
     * 查哪个用户的记录：管理员不传 = 查所有人；
     * 普通用户由后端强制改成自己，前端传了也不算数（防越权）
     */
    private Long userId;
}
