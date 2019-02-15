package com.epam.lab.rto.services;


import com.epam.lab.rto.dao.RouteRepository;
import com.epam.lab.rto.dto.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class RouteService {

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private StationMapService stationMapService;

    public Route createRoute (List <String> stationList) {
        return new Route (createRouteTitle(stationList), stationList);
    }

    public void refreshTravelTimes(Route route) {
        int travelTime = 0;
        int averageSpeed = route.getAverageSpeed();
        if (averageSpeed <= 0) {
            return;
        }

        route.setStationTravelTime(0, travelTime);
        for (int i = 1; i < route.length(); i++) {
            int distance = stationMapService.getDistance(route.getStationName(i - 1), route.getStationName(i));
            travelTime += route.getStationStopDuration(i-1) + 5 * (Math.round(((float) distance / averageSpeed * 60) / 5));
            route.setStationTravelTime(i, travelTime);
        }
    }

    private String createRouteTitle(List<String> stationList) {
        return stationList.get(0).substring(0, 3).toUpperCase() +
                stationList.get(stationList.size() - 1).substring(0, 3).toUpperCase() +
                stationList.size() +
                LocalDate.now().getDayOfMonth();
    }


    public void updateRoute(Route route, String departureDay, String departureTime, String averageSpeed, List <String> times) {
        route.setDepartureDay(DayOfWeek.valueOf(departureDay));
        route.setDepartureTime(LocalTime.parse(departureTime));
        route.setAverageSpeed(Integer.valueOf(averageSpeed));
        for (int i = 1; i < times.size() ; i++) {
            route.setStationStopDuration(i, Integer.valueOf(times.get(i)));
        }
        refreshTravelTimes(route);
    }

    public List<Route> getAllRoutes () {
        return routeRepository.getAllRoutes();
    }

    public List<String> getAllStations() {
        return routeRepository.getAllStations();
    }

    public List<String> findRoutesWithoutTransfer(String departureStation, String arrivalStation) {
        List<String> routes = routeRepository.getRouteTitlesWithStation(departureStation);
        routes.retainAll(routeRepository.getRouteTitlesWithStation(arrivalStation));

        List<String> result = new ArrayList<>();
        for (String routeTitle : routes) {
            List<String> stations = routeRepository.getRouteStationsByTitle(routeTitle);
            if (isBefore(stations, departureStation, arrivalStation)) {
                result.add(routeTitle);
            }
        }
        return result;
    }

    public List<List<String>> findRoutesWithTransfer(String departureStation, String arrivalStation) {
        List<String> departureRoutes = routeRepository.getRouteTitlesWithStation(departureStation);
        List<String> arrivalRoutes = routeRepository.getRouteTitlesWithStation(arrivalStation);
        List<List<String>> result = new ArrayList<>();

        for (String departureRouteTitle : departureRoutes) {
            List<String> departureRouteStations = routeRepository.getRouteStationsByTitle(departureRouteTitle);

            for (String station : departureRouteStations) {

                if (!isBefore(departureRouteStations, departureStation, station)) {
                    continue;
                }
                for (String arrivalRouteTitle : arrivalRoutes) {
                    List<String> arrivalRouteStations = routeRepository.getRouteStationsByTitle(arrivalRouteTitle);
                    if (isBefore(arrivalRouteStations, arrivalStation, station)) {
                        continue;
                    }
                    if (arrivalRouteStations.contains(station)) {
                        result.add(new ArrayList<>(Arrays.asList(departureRouteTitle, station, arrivalRouteTitle)));
                    }
                }
            }
        }
        return result;
    }

    private boolean isBefore(List<String> routeStations, String firstStation, String secondStation) {
        return routeStations.indexOf(firstStation) < routeStations.lastIndexOf(secondStation);
    }

}
