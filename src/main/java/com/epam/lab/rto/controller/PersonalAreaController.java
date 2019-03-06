package com.epam.lab.rto.controller;

import com.epam.lab.rto.dto.User;
import com.epam.lab.rto.dto.UserRole;
import com.epam.lab.rto.manager.UserManager;
import com.epam.lab.rto.service.interfaces.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonalAreaController {

    @Autowired
    private IRequestService requestService;

    @Autowired
    private UserManager userManager;

    @GetMapping(value = {"/", "/home"})
    public ModelAndView home() {
        ModelAndView model = new ModelAndView();

        User currentUser = userManager.getUser();
        if (currentUser.getRole().equals(UserRole.ADMIN)) {
            model.setViewName("redirect:/admin/requests");
        }
        if (currentUser.getRole().equals(UserRole.USER)) {
            model.setViewName("redirect:/personal-area");
        }

        return model;
    }

    @GetMapping("/personal-area")
    public ModelAndView personalArea(@RequestParam(required= false, defaultValue = "false") boolean history) {
        ModelAndView model = new ModelAndView();
        User currentUser = userManager.getUser();

        if (!history){
            model.setViewName("personal/bills");
            model.addObject("requests", requestService.getActiveRequestsByUser(currentUser));
        } else {
            model.setViewName("personal/history");
            model.addObject("requests", requestService.getInactiveRequestsByUser(currentUser));
        }
        model.addObject("user", currentUser);


        return model;
    }
}