package com.coraline.library.aspect;


import com.coraline.library.common.annotation.Log;
import com.coraline.library.entity.OperationLog;
import com.coraline.library.service.OperationLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;


import java.time.LocalDateTime;


@Aspect
@Component
public class OperationLogAspect {


    private final OperationLogService operationLogService;


    public OperationLogAspect(
            OperationLogService operationLogService
    ){

        this.operationLogService = operationLogService;

    }



    @AfterReturning("@annotation(log)")
    public void recordLog(
            JoinPoint joinPoint,
            Log log
    ){


        OperationLog operationLog =
                new OperationLog();


        operationLog.setOperation(
                log.value()
        );


        operationLog.setTarget(
                joinPoint.getTarget()
                        .getClass()
                        .getSimpleName()
        );


        operationLog.setCreateTime(
                LocalDateTime.now()
        );


        operationLog.setUserId(1L);


        operationLogService.record(operationLog);

    }

}
