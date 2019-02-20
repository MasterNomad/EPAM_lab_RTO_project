package com.epam.lab.rto.services;

import com.epam.lab.rto.dao.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RouteMapService {

    @Autowired
    private RouteRepository routeRepository;

    public List<String> findWayWithoutTransfer(String startStation, String finishStation) {
        List<String> result = new ArrayList<>();
        List<String> routes = routeRepository.getRoutesWithStartStation(startStation);
        routes.retainAll(routeRepository.getRoutesWithFinishStation(finishStation));
        for (String title : routes) {
            List stationList = routeRepository.getRouteStations(title);
            if (ifBefore(stationList, startStation, finishStation)) {
                result.add(title);
            }
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

    private boolean ifBefore(List stationList, String startStation, String finishStation) {
        return stationList.indexOf(startStation) < stationList.lastIndexOf(finishStation);
    }

}
