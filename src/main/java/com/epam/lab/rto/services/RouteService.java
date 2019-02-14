package com.epam.lab.rto.services;


import com.epam.lab.rto.dao.RouteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    public List<String> getAllStations() {
        return routeRepository.getAllStations();
    }

    public List<String> findRoutesWithoutTransfer(String departureStation, String arrivalStation) {
        List<String> routes = routeRepository.getRouteTitlesWithStation(departureStation);
        routes.retainAll(routeRepository.getRouteTitlesWithStation(arrivalStation));

        List <String> result = new ArrayList<>();
        for (String routeTitle : routes) {
            List <String> stations = routeRepository.getRouteStationsByTitle(routeTitle);
            if (stations.indexOf(departureStation) < stations.lastIndexOf(arrivalStation)) {
                result.add(routeTitle);
            }
        }

        return result;
    }

    public List<List<String>> findRoutesWithTransfer(String departureStation, String arrivalStation) {

        return null;
    }

}
