package com.epam.lab.rto.services;

import com.epam.lab.rto.dto.Route;
import com.epam.lab.rto.dto.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class FindTrainService {

    @Autowired
    private RouteService routeService;

    @Autowired
    private TripService tripService;

    public List<Trip> findTripsWithoutTransfer(String startStation, String finishStation, LocalDateTime departure) {
        List<Trip> result = new ArrayList<>();

        List<Route> routeList = routeService.findRoutesBetweenStations(startStation, finishStation);
        for (Route route : routeList) {
            LocalDateTime tripDeparture = departure.minus(
                    route.getStationTravelTime(startStation) + route.getStationStopDuration(startStation),
                    ChronoUnit.MINUTES);
            result.addAll(tripService.getTripsByRouteAndDepartureDateTime(route, departure));
        }
        return result;
    }

//    public Map<String, String> findWayWithOneTransfer(String startStation, String finishStation) {
//        Map<String, String> result = new HashMap<>();
//        List<String> startRoutes = routeRepository.getRoutesWithStartStation(startStation);
//        List<String> finishRoutes = routeRepository.getRoutesWithFinishStation(finishStation);
//
//        return result;
//    }

}
