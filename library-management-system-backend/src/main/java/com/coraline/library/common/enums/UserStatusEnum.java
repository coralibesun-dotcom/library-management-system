package com.coraline.library.common.enums;

import lombok.Getter;

@Getter
public enum UserStatusEnum {


    ENABLE(1, "正常"),

    DISABLE(0, "禁用");


    private final Integer code;

    private final String message;


    UserStatusEnum(
            Integer code,
            String message
    ){
        this.code = code;
        this.message = message;
    }

}
