package com.coraline.library.dto;

import lombok.Data;

@Data
public class UserQueryDTO {


    // 当前页
    private Integer pageNum = 1;


    // 每页数量
    private Integer pageSize = 10;


    // 用户名搜索
    private String username;


    // 角色筛选
    private String role;


    // 状态筛选
    private Integer status;

}
