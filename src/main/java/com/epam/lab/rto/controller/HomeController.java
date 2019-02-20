package com.epam.lab.rto.controller;

import com.epam.lab.rto.services.RouteMapService;
import com.epam.lab.rto.services.RouteService;
import com.epam.lab.rto.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private RouteMapService routeMapService;

    @Autowired
    private StationService stationService;

    @GetMapping(value = {"/", "/find-train"})
    public ModelAndView homePage() {
        ModelAndView model = new ModelAndView();
        model.setViewName("find-train");
        model.addObject("stations", stationService.getAllStations());
        return model;
    }

    @PostMapping("/find-train")
    public ModelAndView findTrain(String departureCity, String arrivalCity) {
        ModelAndView model = new ModelAndView();
        model.setViewName("find-train");
        model.addObject("stations", stationService.getAllStations());
        model.addObject("answer", routeMapService.findWayWithoutTransfer(departureCity, arrivalCity));

        return model;
    }

}
