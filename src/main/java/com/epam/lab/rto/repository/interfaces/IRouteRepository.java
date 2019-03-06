package com.epam.lab.rto.repository.interfaces;

import com.epam.lab.rto.dto.Route;

import java.util.List;

public interface IRouteRepository {

    Route addRoute(Route route);

    void updateRoute(Route route);

    int deleteRouteByTitle(String title);

    List<Route> getAllRoutes();

    Route getRouteByTitle(String title);

    List<String> getRouteStationsByTitle(String title);

    List<String> getRoutesWithStartStation(String station);

    List<String> getRoutesWithFinishStation(String station);
}