package com.coraline.library.config;


import com.coraline.library.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {


    private final LoginInterceptor loginInterceptor;


    public WebConfig(
            LoginInterceptor loginInterceptor
    ){

        this.loginInterceptor = loginInterceptor;

    }



    @Override
    public void addInterceptors(
            InterceptorRegistry registry
    ){

        registry.addInterceptor(
                        loginInterceptor
                )
                .addPathPatterns("/**")

                // 登录接口不能拦截
                .excludePathPatterns(
                        "/user/login"
                );

    }

}
