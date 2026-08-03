package com.coraline.library.config;


import com.coraline.library.common.context.UserContext;
import com.coraline.library.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;



@Component
public class LoginInterceptor implements HandlerInterceptor {


    private final JwtUtil jwtUtil;


    public LoginInterceptor(
            JwtUtil jwtUtil
    ){

        this.jwtUtil = jwtUtil;

    }



    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler
    ){


        String token =
                request.getHeader("X-Token");



        if(token == null){

            return false;

        }



        Claims claims =
                jwtUtil.parseToken(token);



        Long userId =
                Long.valueOf(
                        claims.getSubject()
                );


        String role =
                claims.get(
                        "role",
                        String.class
                );



        UserContext.setUserId(userId);

        UserContext.setRole(role);



        return true;

    }




    @Override
    public void afterCompletion(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler,
            Exception ex
    ){

        UserContext.clear();

    }


}
