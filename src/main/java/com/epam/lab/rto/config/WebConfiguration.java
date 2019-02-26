package com.epam.lab.rto.config;

import com.epam.lab.rto.interceptor.AlreadyLoginInterceptor;
import com.epam.lab.rto.interceptor.LoginInterceptor;
import com.epam.lab.rto.interceptor.UserRoleAwareInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Bean
    public LoginInterceptor loginInterceptor() {
        return new LoginInterceptor();
    }

    @Bean
    public AlreadyLoginInterceptor alreadyLoginInterceptor() {
        return new AlreadyLoginInterceptor();
    }

    @Bean
    public UserRoleAwareInterceptor userRoleAwareInterceptor() {
        return new UserRoleAwareInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor()).addPathPatterns("/**")
//                .excludePathPatterns("/login/**", "/css/**", "/js/**", "/img/**", "/error");

        registry.addInterceptor(alreadyLoginInterceptor()).addPathPatterns("/login/**")
                .excludePathPatterns("/css/**", "/js/**", "/img/**");

        registry.addInterceptor(userRoleAwareInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/login/**", "/css/**", "/js/**", "/img/**", "/find-train/carriage-change");
    }

}
