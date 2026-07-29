package com.coraline.library.common;


import com.coraline.library.common.enums.ResultCodeEnum;
import lombok.Data;


@Data
public class Result<T> {


    private Integer code;


    private String message;


    private T data;



    public Result(){

    }



    public Result(
            Integer code,
            String message,
            T data
    ){

        this.code = code;
        this.message = message;
        this.data = data;

    }



    // 成功

    public static <T> Result<T> success(T data){

        return new Result<>(
                ResultCodeEnum.SUCCESS.getCode(),
                ResultCodeEnum.SUCCESS.getMessage(),
                data
        );

    }



    public static <T> Result<T> success(){

        return success(null);

    }



    // 失败

    public static <T> Result<T> error(
            ResultCodeEnum codeEnum
    ){

        return new Result<>(
                codeEnum.getCode(),
                codeEnum.getMessage(),
                null
        );

    }



    // 自定义异常信息

    public static <T> Result<T> error(
            Integer code,
            String message
    ){

        return new Result<>(
                code,
                message,
                null
        );

    }

}
