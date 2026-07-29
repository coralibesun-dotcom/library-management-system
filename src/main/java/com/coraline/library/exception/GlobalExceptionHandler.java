package com.coraline.library.exception;


import com.coraline.library.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
public class GlobalExceptionHandler {



    /**
     * 业务异常
     */
    @ExceptionHandler(BusinessException.class)
    public Result<?> handleBusinessException(
            BusinessException e
    ){

        return Result.error(
                e.getCode(),
                e.getMessage()
        );

    }



    /**
     * 其他未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(
            Exception e
    ){

        return Result.error(
                500,
                "系统异常"
        );

    }

}
