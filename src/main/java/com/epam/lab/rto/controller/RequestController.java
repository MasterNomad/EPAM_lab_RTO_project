package com.epam.lab.rto.controller;

import com.epam.lab.rto.dto.Carriage;
import com.epam.lab.rto.repository.CarriageRepository;
import com.epam.lab.rto.services.RequestService;
import com.epam.lab.rto.services.RouteService;
import com.epam.lab.rto.services.StationService;
import com.epam.lab.rto.services.TripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class RequestController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private RequestService requestService;

    @Autowired
    private StationService stationService;

    @Autowired
    private CarriageRepository carriageRepository;

    @Autowired
    private TripService tripService;

    @GetMapping("/find-train")
    public ModelAndView homePage() {
        ModelAndView model = new ModelAndView();

        model.setViewName("find-train");
        model.addObject("stations", stationService.getAllStations());

        return model;
    }

    @PostMapping("/find-train")
    public ModelAndView findTrain(String departureCity, String destinationCity,
                                  @RequestParam(value = "departure") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime departure) {
        ModelAndView model = new ModelAndView();

        model.setViewName("find-train");
        model.addObject("stations", stationService.getAllStations());
        model.addObject("departureCity", departureCity);
        model.addObject("destinationCity", destinationCity);
        model.addObject("departure", departure);
        model.addObject("answer", requestService.findTrains(departureCity, destinationCity, departure));

        return model;
    }

    @ResponseBody
    @GetMapping("/find-train/carriage-change")
    public List<String> refreshCarriage(long tripId, String carriageName) {
        List<String> answer = new ArrayList<>();
        Carriage carriage = carriageRepository.getCarriageByName(carriageName.trim());
        answer.add(carriage.getDescription());
        answer.add(tripService.getPriceByTripIdAndCarriageId(tripId, carriage.getId()).toString());
        return answer;
    }
}
