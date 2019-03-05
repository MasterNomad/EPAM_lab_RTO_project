package com.epam.lab.rto.controller;

import com.epam.lab.rto.manager.UserManager;
import com.epam.lab.rto.service.RequestService;
import com.epam.lab.rto.service.RouteService;
import com.epam.lab.rto.service.StationService;
import com.epam.lab.rto.service.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class FindTrainController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private StationService stationService;

    @Autowired
    private TripService tripService;

    @Autowired
    private UserManager userManager;

    @GetMapping("/find-train")
    public ModelAndView getFindTrain() {
        ModelAndView model = new ModelAndView();

        model.setViewName("requests/find-train");
        model.addObject("stations", stationService.getAllStations());
        model.addObject("currentDate", LocalDateTime.now().withSecond(0).withNano(0));
        model.addObject("answer", "none");

        return model;
    }

    @PostMapping("/find-train")
    public ModelAndView postFindTrain(String departureCity, String destinationCity,
                                      @RequestParam(value = "departure") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departure) {
        ModelAndView model = new ModelAndView();

        model.setViewName("requests/find-train");
        model.addObject("stations", stationService.getAllStations());
        model.addObject("departureCity", departureCity);
        model.addObject("destinationCity", destinationCity);
        model.addObject("departure", departure);
        model.addObject("currentDate", LocalDateTime.now().withSecond(0).withNano(0));
        model.addObject("answer", requestService.findTrains(departureCity, destinationCity, departure));

        return model;
    }

    @PostMapping("/find-train/сonfirm")
    public ModelAndView confirmRequest(long tripId, String departureCity, String destinationCity, String carriageName) {
        ModelAndView model = new ModelAndView();

        requestService.addRequest(tripId, departureCity, destinationCity, carriageName, userManager.getUser());
        model.setViewName("success");
        model.addObject("page", "find-train");
        model.addObject("msg", "Заявка оформлена");
        model.addObject("additional", "Для оплаты перейдите в личный кабинет");
        model.addObject("link", "/personal-area");

        return model;
    }

    @ResponseBody
    @GetMapping("/find-train/carriage-change")
    public List<String> refreshCarriage(long tripId, String carriageName) {
        return tripService.getPriceAndCarriageDescriptionByTripIdAndCarriageName(tripId, carriageName);
    }
}