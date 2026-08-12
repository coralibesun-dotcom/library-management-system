package com.coraline.library.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class UserQueryDTO {

    // 当前页
    @Min(value = 1, message = "页码必须大于0")
    private Integer pageNum = 1;


    // 每页数量
    @Min(value = 1, message = "每页数量必须大于0")
    @Max(value = 20, message = "每页最多20条")
    private Integer pageSize = 10;


    // 用户名搜索
    private String username;


    // 角色筛选
    private String role;


    // 状态筛选
    private Integer status;

}
