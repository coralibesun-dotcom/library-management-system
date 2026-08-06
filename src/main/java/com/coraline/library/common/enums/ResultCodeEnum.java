package com.coraline.library.common.enums;


import lombok.Getter;


@Getter
public enum ResultCodeEnum {


    SUCCESS(200, "操作成功"),


    PARAM_ERROR(400, "参数错误"),


    UNAUTHORIZED(401, "未登录"),


    FORBIDDEN(403, "无权限"),



    // 用户相关 1000
    USER_NOT_FOUND(1001, "用户不存在"),
    PASSWORD_ERROR(1002, "密码错误"),
    USER_EXIST(1003,"用户名已存在"),


    //JWT相关错误：token过期,token被篡改,token格式错误
    TOKEN_INVALID(1004, "token无效"),


    // 图书相关 2000
    BOOK_NOT_FOUND(2001, "图书不存在"),
    BOOK_STATUS_ERROR(2002, "图书状态错误"),
    STOCK_NOT_ENOUGH(2003, "库存不足"),



    // 借阅相关 3000
    BORROW_ALREADY(3001, "该图书已经借阅"),
    BORROW_LIMIT(3002, "最多借5本书"),
    BORROW_RECORD_NOT_FOUND(3003, "借阅记录不存在"),
    BOOK_RETURNED(3004, "该图书已经归还"),



    //分类
    CATEGORY_NOT_FOUND(4001,"分类不存在"),



    // 系统
    SYSTEM_ERROR(5000, "系统异常");



    private final Integer code;

    private final String message;



    ResultCodeEnum(
            Integer code,
            String message
    ){

        this.code = code;
        this.message = message;

    }

}
