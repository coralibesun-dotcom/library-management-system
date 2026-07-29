package com.coraline.library.exception;


import com.coraline.library.common.enums.ResultCodeEnum;
import lombok.Getter;


@Getter
public class BusinessException extends RuntimeException {


    private final Integer code;



    public BusinessException(
            ResultCodeEnum resultCodeEnum,
            String message
    ){

        super(message);

        this.code = resultCodeEnum.getCode();

    }


}
