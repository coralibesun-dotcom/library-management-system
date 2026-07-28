package com.coraline.library.common.enums;


public enum BorrowStatusEnum {


    /**
     * 借阅中
     */
    BORROWING(0, "借阅中"),


    /**
     * 已归还
     */
    RETURNED(1, "已归还"),


    /**
     * 逾期
     */
    OVERDUE(2, "逾期");


    private Integer code;

    private String desc;


    BorrowStatusEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }


    public Integer getCode() {
        return code;
    }


    public String getDesc() {
        return desc;
    }
}
