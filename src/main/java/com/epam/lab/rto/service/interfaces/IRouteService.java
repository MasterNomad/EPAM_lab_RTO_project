package com.epam.lab.rto.service.interfaces;

import com.epam.lab.rto.dto.Route;

import java.util.List;

public interface IRouteService {

    Route createRoute(List<String> stationList);

    List<Route> getAllRoutes();

    Route getRouteByTitle(String title);

    Route updateRoute(Route route, String locomotive, List<String> times);

    Route saveRoute(Route route);

    boolean deleteRouteByTitle(String title);

    List<Route> findRoutesBetweenStations(String startStation, String finishStation);
}