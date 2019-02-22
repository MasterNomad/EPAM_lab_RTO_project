package com.epam.lab.rto.controller;

import com.epam.lab.rto.repository.CarriageRepository;
import com.epam.lab.rto.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Controller
public class TripController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private CarriageRepository carriageRepository;

    @GetMapping("/admin/schedule")
    public ModelAndView schedule() {
        ModelAndView model = new ModelAndView();

        model.setViewName("schedule/schedule");

        return model;
    }

    @GetMapping("/admin/schedule/add")
    public ModelAndView addSchedule() {
        ModelAndView model = new ModelAndView();

        model.setViewName("schedule/add");
        model.addObject("date", LocalDate.now().plus(1, ChronoUnit.MONTHS));
        model.addObject("routes", routeService.getAllRoutes());
        model.addObject("carriages", carriageRepository.getAll());

        return model;
    }

}
