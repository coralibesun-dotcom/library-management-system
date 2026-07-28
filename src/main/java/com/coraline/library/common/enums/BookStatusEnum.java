package com.coraline.library.common.enums;


public enum BookStatusEnum {


    DRAFT(0,"草稿"),

    ON_SALE(1,"上架"),

    OFF_SALE(2,"下架");


    private Integer code;

    private String desc;


    BookStatusEnum(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode(){
        return code;
    }


    public String getDesc(){
        return desc;
    }
}
