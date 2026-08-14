package com.coraline.library.aspect;


import com.coraline.library.common.annotation.RequireRole;
import com.coraline.library.common.enums.ResultCodeEnum;
import com.coraline.library.exception.BusinessException;
import com.coraline.library.common.context.UserContext;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuthAspect {


    @Before("@annotation(com.coraline.library.common.annotation.RequireRole) "
            + "|| @within(com.coraline.library.common.annotation.RequireRole)")
    public void checkRole(
            JoinPoint joinPoint
    ){


        String currentRole =
                UserContext.getRole();

        // 【新增③】手动获取注解：先找方法上的，找不到再找类上的，都没有就直接放行
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        RequireRole requireRole = signature.getMethod().getAnnotation(RequireRole.class);
        if (requireRole == null) {
            requireRole = joinPoint.getTarget().getClass().getAnnotation(RequireRole.class);
        }
        if (requireRole == null) {
            return; // 没挂 @RequireRole 的接口直接放行（如 /user/login）
        }

        //拿注解里的值
        String[] roles =
                requireRole.value();


        boolean hasPermission = false;


        for(String role : roles){

            if(role.equals(currentRole)){

                hasPermission = true;
                break;

            }

        }


        if(!hasPermission){

            throw new BusinessException(
                    ResultCodeEnum.FORBIDDEN,
                    "没有权限访问"
            );

        }

    }

}
