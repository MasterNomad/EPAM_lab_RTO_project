package com.epam.lab.rto.controller;

import com.epam.lab.rto.dto.User;
import com.epam.lab.rto.exceptions.NoSuchUserException;
import com.epam.lab.rto.exceptions.PasswordNotMatchException;
import com.epam.lab.rto.exceptions.SuchUserAlreadyExistException;
import com.epam.lab.rto.exceptions.WrongAgeException;
import com.epam.lab.rto.manager.UserManager;
import com.epam.lab.rto.service.UserService;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserManager userManager;

    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request) {
        ModelAndView model = new ModelAndView();

        request.getSession().invalidate();
        model.setViewName("redirect:/login");

        return model;
    }

    @GetMapping("/login")
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();

        model.setViewName("login/login");

        return model;
    }

    @PostMapping("/login")
    public ModelAndView enter(User user) {
        ModelAndView model = new ModelAndView();

        try {
            userManager.setUser(userService.enter(user));
            model.setViewName("redirect:/home");
        } catch (NoSuchUserException | PasswordNotMatchException e) {
            model.addObject("answer", e.getMessage());
            model.addObject("email", user.getEmail());
            model.setViewName("login/login");
        }

        return model;
    }


    @GetMapping("/login/registration")
    public ModelAndView registration() {
        ModelAndView model = new ModelAndView();

        model.setViewName("login/registration");
        model.addObject("maxDate", LocalDate.now().minusYears(18));

        return model;
    }

    @GetMapping("/login/registration/success")
    public ModelAndView registrationSuccess() {
        ModelAndView model = new ModelAndView();

        model.setViewName("success");
        model.addObject("page", "login");
        model.addObject("msg", "Вы успешно зарегистрированны");
        model.addObject("link", "/login");

        return model;
    }

    @PostMapping("/login/registration")
    public ModelAndView registerUser(User user, String confirmPassword) {
        ModelAndView model = new ModelAndView();

        try {
            userService.registerUser(user, confirmPassword);
            model.setViewName("redirect:/login/registration/success");
            return model;
        } catch (SuchUserAlreadyExistException e) {
            model.addObject("emailAnswer", e.getMessage());
        } catch (PasswordNotMatchException e) {
            model.addObject("passwordAnswer", e.getMessage());
        } catch (WrongAgeException e) {
            model.addObject("ageAnswer", e.getMessage());
        }
        model.setViewName("login/registration");
        model.addObject("maxDate", LocalDate.now().minusYears(18));
        model.addObject(user);

        return model;
    }
}