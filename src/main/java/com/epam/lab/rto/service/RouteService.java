package com.epam.lab.rto.service;

import com.epam.lab.rto.dto.Route;
import com.epam.lab.rto.repository.interfaces.IRouteRepository;
import com.epam.lab.rto.service.interfaces.IRouteService;
import com.epam.lab.rto.service.interfaces.IStationMapService;
import com.epam.lab.rto.service.interfaces.ITrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService implements IRouteService {

    @Autowired
    private IRouteRepository routeRepository;

    @Autowired
    private ITrainService trainService;

    @Autowired
    private IStationMapService stationMapService;


    @Override
    public Route createRoute(List<String> stationList) {
        return new Route(createRouteTitle(stationList), stationList);
    }

    @Override
    public List<Route> getAllRoutes() {
        return routeRepository.getAllRoutes();
    }

    @Override
    public Route getRouteByTitle(String title) {
        return routeRepository.getRouteByTitle(title);
    }

    @Override
    public Route updateRoute(Route route, String locomotive, List<String> times) {
        route.setLocomotive(trainService.getLocomotiveByName(locomotive.trim()));
        route.setStationStopDuration(0, -1);
        route.setStationStopDuration(route.size() - 1, -2);
        for (int i = 1; i < times.size() - 1; i++) {
            if (times.get(i).matches("\\d+")) {
                route.setStationStopDuration(i, Integer.valueOf(times.get(i)));
            } else {
                route.setStationStopDuration(i, 0);
            }
        }
        refreshTravelTimes(route);
        return route;
    }

    @Override
    public Route saveRoute(Route route) {
        if (routeRepository.getRouteByTitle(route.getTitle()) == null) {
            routeRepository.addRoute(route);
        } else {
            routeRepository.updateRoute(route);
        }
        return route;
    }

    @Override
    public boolean deleteRouteByTitle(String title) {
        if (routeRepository.getRouteByTitle(title) != null) {
            return routeRepository.deleteRouteByTitle(title) > 0;
        }
        return false;
    }

    @Override
    public List<Route> findRoutesBetweenStations(String startStation, String finishStation) {
        List<Route> result = new ArrayList<>();
        List<String> routes = routeRepository.getRoutesWithStartStation(startStation);
        routes.retainAll(routeRepository.getRoutesWithFinishStation(finishStation));
        for (String title : routes) {
            List stationList = routeRepository.getRouteStationsByTitle(title);
            if (ifBefore(stationList, startStation, finishStation)) {
                result.add(routeRepository.getRouteByTitle(title));
            }
        }
        return result;
    }


    private String createRouteTitle(List<String> stationList) {
        return stationList.get(0).substring(0, 3).toUpperCase() +
                stationList.get(stationList.size() - 1).substring(0, 3).toUpperCase() +
                stationList.size() +
                LocalDate.now().getDayOfMonth();
    }

    private void refreshTravelTimes(Route route) {
        int travelTime = 1;
        int averageSpeed = route.getLocomotive().getAverage_speed();
        if (averageSpeed <= 0) {
            route.setAllTravelTime(0);
            return;
        }
        route.setStationTravelTime(0, 0);
        for (int i = 1; i < route.size(); i++) {
            int distance = stationMapService.getDistance(route.getStationName(i - 1), route.getStationName(i));
            travelTime += route.getStationStopDuration(i - 1) + 5 * (Math.round(((float) distance / averageSpeed * 60) / 5));
            route.setStationTravelTime(i, travelTime);
        }
    }

    private boolean ifBefore(List stationList, String startStation, String finishStation) {
        return stationList.indexOf(startStation) < stationList.lastIndexOf(finishStation);
    }
}
