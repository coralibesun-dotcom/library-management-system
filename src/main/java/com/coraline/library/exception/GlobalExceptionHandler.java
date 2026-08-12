package com.coraline.library.exception;

import com.coraline.library.common.Result;
import com.coraline.library.common.enums.ResultCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Result<?>> handleBusinessException(
            BusinessException e
    ){

        HttpStatus status =
                getHttpStatus(e.getCode());


        return ResponseEntity
                .status(status)
                .body(
                        Result.error(
                                e.getCode(),
                                e.getMessage()
                        )
                );

    }


    /**
     * 参数校验异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<?>> handleValidationException(
            MethodArgumentNotValidException e
    ){

        String message =
                e.getBindingResult()
                        .getFieldErrors()
                        .get(0)
                        .getDefaultMessage();


        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(
                        Result.error(
                                ResultCodeEnum.PARAM_ERROR.getCode(),
                                message
                        )
                );

    }


    /**
     * 其他未知异常
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<?>> handleException(
            Exception e
    ){

        log.error(
                "系统异常",
                e
        );


        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        Result.error(
                                ResultCodeEnum.SYSTEM_ERROR.getCode(),
                                ResultCodeEnum.SYSTEM_ERROR.getMessage()
                        )
                );

    }


    /**
     * 业务错误码转换为 HTTP 状态码
     */
    private HttpStatus getHttpStatus(Integer code){

        if(code.equals(ResultCodeEnum.PARAM_ERROR.getCode())){
            return HttpStatus.BAD_REQUEST;
        }

        if(code.equals(ResultCodeEnum.UNAUTHORIZED.getCode())){
            return HttpStatus.UNAUTHORIZED;
        }

        if(code.equals(ResultCodeEnum.FORBIDDEN.getCode())){
            return HttpStatus.FORBIDDEN;
        }

        if(code.equals(ResultCodeEnum.SYSTEM_ERROR.getCode())){
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }

        if(code.equals(ResultCodeEnum.BOOK_NOT_FOUND.getCode())
                || code.equals(ResultCodeEnum.USER_NOT_FOUND.getCode())
                || code.equals(ResultCodeEnum.CATEGORY_NOT_FOUND.getCode())){

            return HttpStatus.NOT_FOUND;
        }

        return HttpStatus.BAD_REQUEST;
    }

}
