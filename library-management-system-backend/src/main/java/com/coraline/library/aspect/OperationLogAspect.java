package com.coraline.library.aspect;


import com.coraline.library.common.annotation.Log;
import com.coraline.library.common.context.UserContext;
import com.coraline.library.entity.OperationLog;
import com.coraline.library.service.OperationLogService;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;



@Aspect
@Component
public class OperationLogAspect {


    private final OperationLogService operationLogService;


    public OperationLogAspect(
            OperationLogService operationLogService
    ) {

        this.operationLogService = operationLogService;

    }


    @AfterReturning("@annotation(log)")
    public void recordLog(
            JoinPoint joinPoint,
            Log log
    ) {


        try {


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



            operationLog.setUserId(
                    UserContext.getUserId()
            );


            operationLogService.record(
                    operationLog
            );


        } catch (Exception e) {

            // 日志失败不能影响业务结果

            e.printStackTrace();

        }

    }
}
