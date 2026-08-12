package com.coraline.library.aspect;


import com.coraline.library.common.annotation.RequireRole;
import com.coraline.library.common.enums.ResultCodeEnum;
import com.coraline.library.exception.BusinessException;
import com.coraline.library.common.context.UserContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;


@Aspect
@Component
public class AuthAspect {


    @Before("@annotation(requireRole) || @within(requireRole)")
    public void checkRole(
            RequireRole requireRole
    ){


        String currentRole =
                UserContext.getRole();


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
