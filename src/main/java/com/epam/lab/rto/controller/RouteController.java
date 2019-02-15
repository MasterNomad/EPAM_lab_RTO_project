package com.epam.lab.rto.controller;

import com.epam.lab.rto.services.RouteService;
import com.epam.lab.rto.services.StationMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private StationMapService stationMapService;

    @GetMapping("/route")
    public ModelAndView routeList () {
        ModelAndView model = new ModelAndView();
        model.setViewName("route");
        model.addObject("routes", routeService.getAllRoutes());

        return model;
    }

    @GetMapping ("/route-create")
    public ModelAndView routeCreate () {
        ModelAndView model = new ModelAndView();
        model.setViewName("route-create");
        model.addObject("stations", routeService.getAllStations());

        return model;
    }

    @PostMapping ("/route-create")
    public ModelAndView generateRoute (@RequestParam("args[]") String... args) {
        ModelAndView model = new ModelAndView();
        model.setViewName("route-create");
        model.addObject("stations", routeService.getAllStations());
        model.addObject("answer",stationMapService.createRouteWay(args));

        return model;
    }
}
