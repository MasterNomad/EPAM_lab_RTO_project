package com.epam.lab.rto.dao;


import com.epam.lab.rto.dto.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class RouteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StationMapRepository stationMapRepository;

    public Route createRoute(List<String> stationList, DayOfWeek departureDay, LocalTime departureTime, int defaultStopDuration, int averageSpeed) {
        Route result = new Route(createRouteTitle(stationList), stationList, departureDay, departureTime, defaultStopDuration, averageSpeed);
        refreshArrivalTimes(result);
        return result;
    }


    public void addRoute(Route route) {
        String sql = "INSERT INTO routes (`title`, `order`, `station_name`, `arrival_day`, `arrival_time`, `stop_duration`) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        for (int i = 0; i < route.length(); i++) {
            jdbcTemplate.update(sql,
                    route.getTitle(),
                    i,
                    route.getStationName(i),
                    route.getStationArrivalDay(i).toString(),
                    route.getStationArrivalTime(i),
                    route.getStationStopDuration(i));
        }
    }

    public Route getRoutByTitle(String title) {
        String sql = "SELECT * " +
                "FROM rto.routes " +
                "WHERE title = ? " +
                "ORDER BY `order`";

        Route result = new Route(title);
        List<Map<String, Object>> sqlResult = jdbcTemplate.queryForList(sql, title);
        for (Map<String, Object> entry : sqlResult) {
            result.addStation(entry.get("station_name").toString(),
                    DayOfWeek.valueOf(entry.get("arrival_day").toString()),
                    LocalTime.parse(entry.get("arrival_time").toString()),
                    Integer.valueOf(entry.get("stop_duration").toString()));
        }

        return result;
    }

    public List<String> getAllStations() {
        String sql = "SELECT name " +
                "FROM stations " +
                "ORDER BY id";

        return jdbcTemplate.queryForList(sql, String.class);
    }

//    public List<Route> findRoutes(String stationName) {
//        String sql = "SELECT title " +
//                "FROM rto.routes " +
//                "Where station_name = ?";
//        List<String> titleList = jdbcTemplate.queryForList(sql, new Object[]{stationName}, String.class);
//        List<Route> result = new ArrayList<>();
//        for (String title : titleList) {
//            Route tmpRoute = getRoutByTitle(title);
//            if (tmpRoute.isBefor(startStation, finishStation)) {
//                result.add(tmpRoute);
//            }
//        }
//        return result;
//    }

    public List<String> getRouteStationsByTitle(String title) {
        String sql = "SELECT name " +
                "FROM routes" +
                "WHERE title = ? " +
                "ORDER BY `order`";
        return jdbcTemplate.queryForList(sql, new Object[]{title}, String.class);
    }

    public Map<String, List<String>> getRouteWayWithStation(String stationName) {
        List<String> routeList = getRouteTitlesWithStation(stationName);
        String sql = "SELECT station_name " +
                "FROM routes " +
                "WHERE title = ? " +
                "ORDER BY `order`";
        Map<String, List<String>> result = new HashMap<>();
        for (String title : routeList) {
            List<String> stations = jdbcTemplate.queryForList(sql, new Object[]{title}, String.class);
            result.put(title, stations);
        }

        return result;
    }

    public List<String> getRouteTitlesWithStation(String stationName) {
        String sql = "SELECT distinct title " +
                "FROM routes " +
                "WHERE station_name = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{stationName}, String.class);
    }

    private void refreshArrivalTimes(Route route) {

        DayOfWeek departureDay = route.getDepartureDay();
        LocalTime departureTime = route.getDepartureTime();
        int averageSpeed = route.getAverageSpeed();
        DayOfWeek arrivalDay;
        LocalTime arrivalTime;

        for (int i = 1; i < route.length(); i++) {
            int distance = stationMapRepository.getDistance(route.getStationName(i - 1), route.getStationName(i));
            int travelTime = 5 * (Math.round(((float) distance / averageSpeed * 60) / 5));
            arrivalDay = departureDay.plus((departureTime.getHour() * 60 + departureTime.getMinute() + travelTime) / 1440);
            arrivalTime = departureTime.plusMinutes(travelTime);

            int stopDuration = route.getStationStopDuration(i);
            departureDay = arrivalDay.plus((arrivalTime.getHour() * 60 + arrivalTime.getMinute() + stopDuration) / 1440);
            departureTime = arrivalTime.plusMinutes(stopDuration);

            route.setStationArrivalDayTime(i, arrivalDay, arrivalTime);
        }
    }

    private String createRouteTitle(List<String> stationList) {
        return stationList.get(0).substring(0, 3).toUpperCase() +
                stationList.get(stationList.size() - 1).substring(0, 3).toUpperCase() +
                stationList.size() +
                LocalDate.now().getDayOfMonth();
    }
}
