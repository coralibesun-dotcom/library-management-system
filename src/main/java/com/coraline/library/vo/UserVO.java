package com.coraline.library.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserVO {

    private Long id;

    private String username;

    private String role;

    private Integer status;

    private LocalDateTime createTime;

}
