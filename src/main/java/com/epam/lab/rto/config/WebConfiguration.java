package com.epam.lab.rto.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(alreadyLoginInterceptor()).addPathPatterns("/login/**");

//        registry.addInterceptor(userRoleAwareInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/login/**", "/css/**", "/js/**", "/img/**", "/find-train/carriage-change", "/route/getstations", "/route/getlocomotivespeed");
    }

}
