package com.epam.lab.rto.dao;


import com.epam.lab.rto.dto.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public class RouteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StationMapRepository stationMapRepository;

    private RowMapper<Route> ROW_MAPPER = (rs, rowNum) -> new Route(rs.getString("title"),
            DayOfWeek.valueOf(rs.getString("departure_day")),
            rs.getTime("departure_time").toLocalTime(),
            rs.getInt("averge_speed"));


//    public void addRoute(Route route) {
//        String sql = "INSERT INTO route_stations (`title`, `order`, `station_name`, `travel_time`, `stop_duration`) " +
//                "VALUES (?, ?, ?, ?, ?)";
//
//        for (int i = 0; i < route.length(); i++) {
//            jdbcTemplate.update(sql,
//                    route.getTitle(),
//                    i,
//                    route.getStationName(i),
//                    route.getStationTravelTime(i),
//                    route.getStationStopDuration(i));
//        }
//    }

    public List<Route> getAllRoutes() {
        String sql = "SELECT * " +
                "FROM routes";

        List<Route> result = jdbcTemplate.query(sql, ROW_MAPPER);
        for (Route route : result) {
            fillRouteStations(route);
        }

        return result;
    }

    public Route getRouteByTitle(String title) {
        String sql = "SELECT * " +
                "FROM routes " +
                "WHERE title = ? ";
        Route result = jdbcTemplate.queryForObject(sql, ROW_MAPPER, title);
        fillRouteStations(result);
        return result;
    }

    private void fillRouteStations(Route route) {
        String sql = "SELECT * " +
                "FROM route_stations " +
                "WHERE title = ? " +
                "ORDER BY `order`";
        SqlRowSet request = jdbcTemplate.queryForRowSet(sql, route.getTitle());
        while (request.next()) {
            route.addStation(request.getString("station_name"),
                    request.getInt("stop_duration"),
                    request.getInt("travel_time"));
        }
    }

//    public Route getRoutByTitle(String title) {

//        List<Map<String, Object>> sqlResult = jdbcTemplate.queryForList(sql, title);
//        for (Map<String, Object> entry : sqlResult) {
//            result.addStation(entry.get("station_name").toString(),
//                   Integer.valueOf(entry.get("travel_time").toString()),
//                    Integer.valueOf(entry.get("stop_duration").toString()));
//        }
//
//        return result;
//    }

    public List<String> getAllStations() {
        String sql = "SELECT name " +
                "FROM stations " +
                "ORDER BY id";

        return jdbcTemplate.queryForList(sql, String.class);
    }

    public List<String> getRouteStationsByTitle(String title) {
        String sql = "SELECT station_name " +
                "FROM route_stations " +
                "WHERE title = ? " +
                "ORDER BY `order`";
        return jdbcTemplate.queryForList(sql, new Object[]{title}, String.class);
    }

    public List<String> getRouteTitlesWithStation(String stationName) {
        String sql = "SELECT distinct title " +
                "FROM route_stations " +
                "WHERE station_name = ?";
        return jdbcTemplate.queryForList(sql, new Object[]{stationName}, String.class);
    }

}
