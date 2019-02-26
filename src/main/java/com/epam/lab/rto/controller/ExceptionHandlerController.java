package com.epam.lab.rto.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ExceptionHandlerController {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleRuntimeException(Exception ex) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        return modelAndView;
    }

}
