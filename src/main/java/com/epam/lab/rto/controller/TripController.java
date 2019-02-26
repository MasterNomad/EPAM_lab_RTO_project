package com.epam.lab.rto.controller;

import com.epam.lab.rto.repository.CarriageRepository;
import com.epam.lab.rto.services.RouteService;
import com.epam.lab.rto.services.TripService;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.jws.WebParam;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Controller
public class TripController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private TripService tripService;

    @Autowired
    private CarriageRepository carriageRepository;

    @GetMapping("/admin/schedule")
    public ModelAndView getSchedule() {
        ModelAndView model = new ModelAndView();

        LocalDate firstDate = LocalDate.now();
        LocalDate secondDate = LocalDate.now().plus(1, ChronoUnit.MONTHS);

        model.setViewName("schedule/schedule");
        model.addObject("firstDate", firstDate);
        model.addObject("secondDate", secondDate);
        model.addObject("trips", tripService.getTripsBetweenDates(firstDate, secondDate));

        return model;
    }

    @GetMapping("/admin/schedule/add")
    public ModelAndView addScheduleMenu() {
        ModelAndView model = new ModelAndView();

        model.setViewName("schedule/add");
        model.addObject("currentDate", LocalDateTime.now().withSecond(0).withNano(0));
        model.addObject("date", LocalDate.now().plus(1, ChronoUnit.MONTHS));
        model.addObject("routes", routeService.getAllRoutes());
        model.addObject("carriages", carriageRepository.getAll());

        return model;
    }

    @PostMapping("/admin/schedule")
    public ModelAndView postSchedule(@RequestParam(value = "firstDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
                                     @RequestParam(value = "secondDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate secondDate) {
        ModelAndView model = new ModelAndView();

        model.setViewName("schedule/schedule");
        model.addObject("firstDate", firstDate);
        model.addObject("secondDate", secondDate);
        model.addObject("trips", tripService.getTripsBetweenDates(firstDate, secondDate));

        return model;
    }

    @PostMapping("/admin/schedule/add")
    public ModelAndView addSchedule(@RequestParam(value = "route[]") String[] routes,
                                    @RequestParam(value = "departure[]") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime[] departures,
                                    @RequestParam(value = "price[]") BigDecimal[] prices,
                                    @RequestParam(value = "carriage[]") Integer[] carriages,
                                    @RequestParam(value = "repeat[]") Long[] repeats,
                                    @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        tripService.addSchedule(routes, departures, prices, carriages, repeats, date);
        ModelAndView model = new ModelAndView();
        model.setViewName("/schedule/schedule-success");

        return model;
    }

}
