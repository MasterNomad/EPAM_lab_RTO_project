package com.epam.lab.rto.controller;

import com.epam.lab.rto.dto.Trip;
import com.epam.lab.rto.service.interfaces.IRouteService;
import com.epam.lab.rto.service.interfaces.IStationMapService;
import com.epam.lab.rto.service.interfaces.ITrainService;
import com.epam.lab.rto.service.interfaces.ITripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

@Controller
public class ScheduleController {

    @Autowired
    private IStationMapService stationMapService;

    @Autowired
    private IRouteService routeService;

    @Autowired
    private ITripService tripService;

    @Autowired
    private ITrainService trainService;


    @GetMapping("/schedule")
    public ModelAndView getSchedule(@RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate firstDate,
                                    @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate secondDate,
                                    @RequestParam(defaultValue = "1") int page) {
        ModelAndView model = new ModelAndView();
        int count = 15;
        int pages;

        if (Objects.isNull(firstDate)) {
            firstDate = LocalDate.now();
        }
        if (Objects.isNull(secondDate)) {
            secondDate = firstDate.plusMonths(1);
        }

        model.setViewName("schedule/schedule");
        model.addObject("stations", stationMapService.getAllStations());
        model.addObject("firstDate", firstDate);
        model.addObject("secondDate", secondDate);

        List<Trip> trips = tripService.getTripsBetweenDates(firstDate, secondDate);
        int tripSize = trips.size();
        if (tripSize > count) {
            pages = tripSize / count;
            if (tripSize > page * count) {
                trips = trips.subList(page * count - count, page * count);
            } else {
                trips = trips.subList(page * count - count, tripSize - 1);
            }
            model.addObject("page", page);
            model.addObject("pages", pages);
        }
        model.addObject("trips", trips);

        return model;
    }

    @GetMapping("/admin/schedule/add")
    public ModelAndView addScheduleMenu() {
        ModelAndView model = new ModelAndView();

        model.setViewName("schedule/add");
        model.addObject("date", LocalDate.now().plus(1, ChronoUnit.MONTHS));
        model.addObject("routes", routeService.getAllRoutes());
        model.addObject("carriages", trainService.getAllCarriages());

        return model;
    }

    @PostMapping("/admin/schedule/add")
    public ModelAndView addSchedule(@RequestParam(value = "route[]") String[] routes,
                                    @RequestParam(value = "departure[]") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime[] departures,
                                    @RequestParam(value = "price[]") BigDecimal[] prices,
                                    @RequestParam(value = "carriage[]") Integer[] carriages,
                                    @RequestParam(value = "repeat[]") Long[] repeats,
                                    @RequestParam(value = "date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Trip> schedule = tripService.createSchedule(routes, departures, prices, carriages, repeats, date);
        tripService.addSchedule(schedule);

        ModelAndView model = new ModelAndView();
        model.setViewName("success");
        model.addObject("page", "schedule");
        model.addObject("msg", "Расписание обновлено");
        model.addObject("link", "/schedule");

        return model;
    }
}