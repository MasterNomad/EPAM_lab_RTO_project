package com.epam.lab.rto.services;

import com.epam.lab.rto.dao.RouteRepository;
import com.epam.lab.rto.dto.RouteMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RouteMapService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private RouteMap routeMap;

    public List<String> findWayWithoutTransfer(String startStation, String finishStation) {
        List<String> routes = routeRepository.getRoutesWithStation(startStation);
        routes.retainAll(routeRepository.getRoutesWithStation(finishStation));
        List<String> result = new ArrayList<>();
        for (String title : routes) {
            List stationList = routeRepository.getRouteStations(title);
            if (ifBefore(stationList, startStation, finishStation)) {
                result.add(title);
            }
        }
        return result;
    }

    public Map<String, String> findWayWithTransfer(String startStation, String finishStation) {
        Map<String, String> result = new HashMap<>();
        List<String> startRoutes = routeRepository.getRoutesWithStation(startStation);
        List<String> finishRoutes = routeRepository.getRoutesWithStation(finishStation);
        for (String title : startRoutes) {
            for (String connectTitle : routeMap.getConnectedRoutes(title)) {
                if (finishRoutes.contains(connectTitle)) {
                    result.put(title, connectTitle);
                }
            }
        }
        return result;
    }

    @PostConstruct
    public void refreshRouteMap() {
        routeMap.clear();
        fillMapRoutes();
        fillMapConnections();
    }

    private void fillMapRoutes() {
        routeMap.add(routeRepository.getAllRouteTitles());
    }

    private void fillMapConnections() {
        for (String title : routeMap.getMapRoutes()) {
            List<String> stationList = routeRepository.getRouteStations(title);
            stationList.remove(0);
            for (String station : stationList) {
                for (String connectRoute : routeRepository.getConnectedRoutes(station)) {
                    routeMap.addConnection(title, connectRoute, station);
                }
            }
        }
    }

    private boolean ifBefore(List stationList, String startStation, String finishStation) {
        return stationList.indexOf(startStation) < stationList.lastIndexOf(finishStation);
    }

}
