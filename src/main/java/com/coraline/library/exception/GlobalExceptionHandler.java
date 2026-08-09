package com.coraline.library.exception;


import com.coraline.library.common.Result;
import com.coraline.library.common.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
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
     * 参数校验异常
     *
     * 例如：
     * pageNum=0
     * username为空
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> handleValidationException(
            MethodArgumentNotValidException e
    ){

        String message =
                e.getBindingResult()
                        .getFieldErrors()
                        .get(0)
                        .getDefaultMessage();


        return Result.error(
                ResultCodeEnum.PARAM_ERROR.getCode(),
                message
        );

    }

    /**
     * 其他未知异常
     */
    @ExceptionHandler(Exception.class)
    public Result<?> handleException(
            Exception e
    ){

        log.error(
                "系统异常",
                e
        );
        return Result.error(
                ResultCodeEnum.SYSTEM_ERROR.getCode(),
                ResultCodeEnum.SYSTEM_ERROR.getMessage()
        );

    }

}
