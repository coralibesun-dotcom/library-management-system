package com.coraline.library.exception;


import com.coraline.library.common.enums.ResultCodeEnum;
import lombok.Getter;


@Getter
public class BusinessException extends RuntimeException {


    private final Integer code;


    /**
     * 自定义提示信息
     */
    public BusinessException(
            ResultCodeEnum resultCodeEnum,
            String message
    ){

        super(message);

        this.code = resultCodeEnum.getCode();

    }



    /**
     * 使用枚举默认提示
     */
    public BusinessException(
            ResultCodeEnum resultCodeEnum
    ){

        super(resultCodeEnum.getMessage());

        this.code = resultCodeEnum.getCode();

    }

}
