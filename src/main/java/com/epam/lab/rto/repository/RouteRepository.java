package com.epam.lab.rto.repository;

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
    private StationRepository stationRepository;

    @Autowired
    LocomotiveRepository locomotiveRepository;

    private RowMapper<Route> ROW_MAPPER = (rs, rowNum) -> new Route(rs.getString("title"),
            locomotiveRepository.getLocomotiveById(rs.getLong("locomotive_id")));

    public Route addRoute(Route route) {
        String sql = "INSERT INTO routes " +
                "(`title`, `locomotive_id`) " +
                "VALUES (?, ?)";
        jdbcTemplate.update(sql, route.getTitle(), route.getLocomotive().getId());

        sql = "INSERT INTO route_stations " +
                "(`title`, `order`, `station_name`, `stop_duration`, `travel_time`) " +
                "VALUES (?, ?, ?, ?, ?)";
        int order = 0;
        for (Route.Station station : route.getStations()) {
            jdbcTemplate.update(sql, route.getTitle(),
                    order++,
                    station.getName(),
                    station.getStopDuration(),
                    station.getTravelTime());
        }
        return route;
    }

    public void updateRoute(Route route) {
        String sql = "UPDATE routes " +
                "SET `locomotive_id` = ? " +
                "WHERE `title` = ? ";
        jdbcTemplate.update(sql, route.getLocomotive().getId(), route.getTitle());

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

    public void deleteRouteByTitle(String title) {
        String sql = "DELETE FROM `route_stations` " +
                "WHERE `title` = ?";
        jdbcTemplate.update(sql, title);

        sql = "DELETE FROM `routes` " +
                "WHERE `title` = ?";
        jdbcTemplate.update(sql, title);
    }

    public List<Route> getAllRoutes() {
        String sql = "SELECT * " +
                "FROM `routes`";

        List<Route> result = jdbcTemplate.query(sql, ROW_MAPPER);
        for (Route route : result) {
            fillRouteStations(route);
        }

        return result;
    }

    public Route getRouteByTitle(String title) {
        String sql = "SELECT * " +
                "FROM `routes` " +
                "WHERE `title` = ? ";
        try {
            Route result = jdbcTemplate.queryForObject(sql, ROW_MAPPER, title);
            fillRouteStations(result);
            return result;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<String> getRouteStations(String title) {
        String sql = "SELECT `station_name` " +
                "FROM `route_stations` " +
                "WHERE `title` = ? " +
                "ORDER BY `order`";
        return jdbcTemplate.queryForList(sql, String.class, title);

    }

    public List<String> getRoutesWithStartStation(String station) {
        String sql = "SELECT `title` " +
                "FROM `route_stations` " +
                "WHERE `station_name` = ? AND `stop_duration` <> -2 AND `stop_duration` <> 0";
        return jdbcTemplate.queryForList(sql, String.class, station);
    }

    public List<String> getRoutesWithFinishStation(String station) {
        String sql = "SELECT `title` " +
                "FROM `route_stations` " +
                "WHERE `station_name` = ? AND `stop_duration` <> -1 OR `stop_duration` <> 0";
        return jdbcTemplate.queryForList(sql, String.class, station);
    }

    private void fillRouteStations(Route route) {
        String sql = "SELECT * " +
                "FROM `route_stations` " +
                "WHERE `title` = ? " +
                "ORDER BY `order`";
        SqlRowSet request = jdbcTemplate.queryForRowSet(sql, route.getTitle());
        while (request.next()) {
            route.addStation(request.getString("station_name"),
                    request.getInt("stop_duration"),
                    request.getInt("travel_time"));
        }
    }
}
