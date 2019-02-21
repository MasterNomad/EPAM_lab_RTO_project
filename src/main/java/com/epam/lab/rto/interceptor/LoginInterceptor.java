package com.epam.lab.rto.interceptor;

import com.epam.lab.rto.dto.User;
import com.epam.lab.rto.manager.UserManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class LoginInterceptor implements HandlerInterceptor {

    @Autowired
    UserManager userManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        User currentUser = userManager.getUser();

        if (Objects.isNull(currentUser)) {
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
