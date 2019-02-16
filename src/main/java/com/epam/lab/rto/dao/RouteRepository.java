package com.epam.lab.rto.dao;

import com.epam.lab.rto.dto.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RouteRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private StationMapRepository stationMapRepository;

    private RowMapper<Route> ROW_MAPPER = (rs, rowNum) -> new Route(rs.getString("title"),
            rs.getInt("average_speed"));

    public void addRoute(Route route) {
        String sql = "INSERT INTO routes " +
                "(`title`, `average_speed`) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql, route.getTitle(), route.getAverageSpeed());

        sql = "INSERT INTO route_stations " +
                "(`title`, `order`, `station_name`, `stop_duration`, `travel_time`) " +
                "VALUES (?, ?, ?, ?, ?)";
        int order = 0;
        for (Route.Station station : route.getStations()) {
            jdbcTemplate.update(sql, route.getTitle(),
                    order++,
                    station.getStationName(),
                    station.getStopDuration(),
                    station.getTravelTime());
        }
    }

    public void updateRoute(Route route) {
        String sql = "UPDATE routes " +
                "SET `average_speed` = ? " +
                "WHERE `title` = ? ";
        jdbcTemplate.update(sql, route.getAverageSpeed(), route.getTitle());

        sql = "UPDATE `route_stations` " +
                "SET `stop_duration` = ?, `travel_time` = ? " +
                "WHERE (`title` = ?) and (`order` = ?)";

        int order = 0;
        for (Route.Station station : route.getStations()) {
            jdbcTemplate.update(sql, station.getStopDuration(),
                    station.getTravelTime(),
                    route.getTitle(),
                    order++);
        }
    }

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
        try {
            Route result = jdbcTemplate.queryForObject(sql, ROW_MAPPER, title);
            fillRouteStations(result);
            return result;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
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
