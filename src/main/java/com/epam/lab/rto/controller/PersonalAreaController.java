package com.epam.lab.rto.controller;

import com.epam.lab.rto.dto.User;
import com.epam.lab.rto.repository.interfaces.IUserRepository;
import com.epam.lab.rto.service.interfaces.IRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class PersonalAreaController {

    @Autowired
    private IRequestService requestService;

    @Autowired
    private IUserRepository userRepository;

    @GetMapping(value = {"/", "/home"})
    public ModelAndView home(Authentication authentication) {
        ModelAndView model = new ModelAndView();

        if (authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ADMIN"))) {
            model.setViewName("redirect:/admin/requests");
        }
        if (authentication.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("USER"))) {
            model.setViewName("redirect:/personal-area");
        }

        return model;
    }

    @GetMapping("/personal-area")
    public ModelAndView personalArea(@RequestParam(required = false, defaultValue = "false") boolean history, Authentication authentication) {
        ModelAndView model = new ModelAndView();
        User currentUser = userRepository.getUserByEmail(authentication.getName());

        if (!history) {
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