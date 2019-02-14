package com.epam.lab.rto.dao;


import com.epam.lab.rto.dto.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

import java.util.List;
import java.util.Map;

@Repository
public class RouteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StationMapRepository stationMapRepository;

    public void addRoute(Route route) {
        String sql = "INSERT INTO routes (`title`, `order`, `station_name`, `travel_time`, `stop_duration`) " +
                "VALUES (?, ?, ?, ?, ?)";

        for (int i = 0; i < route.length(); i++) {
            jdbcTemplate.update(sql,
                    route.getTitle(),
                    i,
                    route.getStationName(i),
                    route.getStationTravelTime(i),
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
                   Integer.valueOf(entry.get("travel_time").toString()),
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

    public List<String> getRouteStationsByTitle(String title) {
        String sql = "SELECT station_name " +
                "FROM routes " +
                "WHERE title = ? " +
                "ORDER BY `order`";
        return jdbcTemplate.queryForList(sql, new Object[]{title}, String.class);
    }

    public List<String> getRouteTitlesWithStation(String stationName) {
        String sql = "SELECT distinct title " +
                "FROM routes " +
                "WHERE station_name = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{stationName}, String.class);
    }

}
