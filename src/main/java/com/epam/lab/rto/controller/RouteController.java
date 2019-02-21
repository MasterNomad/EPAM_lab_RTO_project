package com.epam.lab.rto.controller;

import com.epam.lab.rto.dao.LocomotiveRepository;
import com.epam.lab.rto.dto.Route;
import com.epam.lab.rto.manager.RouteManager;
import com.epam.lab.rto.services.RouteService;
import com.epam.lab.rto.services.StationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private StationService stationService;

    @Autowired
    private LocomotiveRepository locomotiveRepository;

    @Autowired
    private RouteManager routeManager;

    @GetMapping("/route")
    public ModelAndView routeList() {
        ModelAndView model = new ModelAndView();
        model.setViewName("route/route");
        model.addObject("routes", routeService.getAllRoutes());

        return model;
    }

    @GetMapping("/route/create")
    public ModelAndView routeCreate() {
        ModelAndView model = new ModelAndView();
        model.setViewName("route/create");
        model.addObject("stations", stationService.getAllStations());
        List<String> routeWay = routeManager.getRouteWay();
        if (routeWay != null) {
            model.addObject("answer", routeWay);
            model.addObject("next", "true");
        }
        model.addObject("next", "none");

        return model;
    }

    @PostMapping("/route/create")
    public ModelAndView generateRoute(@RequestParam(value = "args[]") String... args) {
        ModelAndView model = new ModelAndView();
        model.setViewName("route/create");
        model.addObject("stations", stationService.getAllStations());
        List<String> routeWay = stationService.createRouteWay(args);
        model.addObject("answer", routeWay);
        model.addObject("next", "true");
        routeManager.setRouteWay(routeWay);

        return model;
    }

    @GetMapping("/route/edit")
    public ModelAndView editRoute(String title) {
        ModelAndView model = new ModelAndView();
        model.setViewName("route/edit");
        List<String> routeWay = routeManager.getRouteWay();
        Route route;

        if (routeWay == null && title == null) {
            return model;
        }
        if (title == null) {
            route = routeService.createRoute(routeWay);
            routeManager.setCurrentRoute(route);
        } else if (title.equals("update")) {
            route = routeManager.getCurrentRoute();
            model.addObject("answer", "Маршрут обновлён, но не сохрнанён!");
        } else if (title.equals("save")) {
            route = routeManager.getCurrentRoute();
            model.addObject("answer", "Маршрут сохрнанён в базе данных.");
        } else {
            route = routeService.getRouteByTitle(title);
            routeManager.setCurrentRoute(route);
        }

        model.addObject("route", route);
        model.addObject("stations", route.getStations());
        model.addObject("locomotives", locomotiveRepository.getAll());
        return model;
    }

    @PostMapping("/route/update")
    public ModelAndView updateRoute(String locomotive, @RequestParam(value = "time[]") String[] times, String action) {
        Route route = routeManager.getCurrentRoute();
        routeService.updateRoute(route, locomotive, new ArrayList<>(Arrays.asList(times)));
        if (action.equals("save")) {
            routeService.saveRoute(route);
        }
        return editRoute(action);
    }

    @GetMapping("/route/delete")
    public ModelAndView deleteRoute (@RequestParam String title) {
        routeService.deleteRouteByTitle(title);
        return routeList();
    }

}
