package com.coraline.library.interceptor;


import com.coraline.library.common.context.UserContext;
import com.coraline.library.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;


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
    )throws IOException {


        String token =
                request.getHeader("X-Token");


        if (token == null || token.isEmpty()) {

            String auth =
                    request.getHeader("Authorization");

            if (auth != null && auth.startsWith("Bearer ")) {

                token = auth.substring(7);

            }

        }


        if (token == null || token.isEmpty()) {


            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );


            response.setContentType(
                    "application/json;charset=UTF-8"
            );


            response.getWriter()
                    .write(
                            """
                                    {
                                        "code":401,
                                        "message":"请先登录"
                                    }
                                    """
                    );


            return false;

        }


        try {


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


        } catch (Exception e) {


            //Token解析失败，也返回401
            response.setStatus(
                    HttpServletResponse.SC_UNAUTHORIZED
            );


            response.setContentType(
                    "application/json;charset=UTF-8"
            );


            response.getWriter()
                    .write(
                            """
                                    {
                                        "code":401,
                                        "message":"登录已失效，请重新登录"
                                    }
                                    """
                    );


            return false;

        }
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
