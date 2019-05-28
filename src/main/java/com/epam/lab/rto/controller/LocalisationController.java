package com.epam.lab.rto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LocalisationController {

    @GetMapping("/change-lang")
    public String getInternationalPage(HttpServletRequest request) {
        return request.getHeader("referer");
    }

}
