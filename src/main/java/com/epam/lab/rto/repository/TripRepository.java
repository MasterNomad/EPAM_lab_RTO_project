package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TripRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RouteRepository routerouteRepository;

    private RowMapper<Trip> ROW_MAPPER = (rs, rowNum) -> new Trip(rs.getLong("trip_id"),
            routerouteRepository.getRouteByTitle(rs.getString("route")),
           rs.getTimestamp("departure").toLocalDateTime(),
            rs.getBigDecimal("price"));

    public int addTrip(Trip trip) {
        String sql = "INSERT INTO `rto`.`trips` " +
                "(`route`, `departure`, `price`) " +
                "VALUES (?, ?, ?);";
      return jdbcTemplate.update(sql, trip.getRoute().getTitle(), trip.getDeparture(), trip.getPrice());
    }

    public List<Trip> getTripsBetweenDates(LocalDate firstDate, LocalDate secondDate) {
        String sql = "SELECT * " +
                "FROM `trips` " +
                "WHERE `departure` BETWEEN ? and ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, firstDate, secondDate);
    }
}
