package com.epam.lab.rto.service.interfaces;

import java.util.List;

public interface IStationMapService {

    List<String> getAllStations();

    List<String> createRouteWay(String... args);

    int getDistance(String startStation, String finishStation);
}