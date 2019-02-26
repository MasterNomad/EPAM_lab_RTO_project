package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.Trip;
import com.epam.lab.rto.dto.TripComposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class TripRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private TripCompositionRepository tripCompositionRepository;

    private RowMapper<Trip> ROW_MAPPER = (rs, rowNum) -> new Trip(rs.getLong("trip_id"),
            routeRepository.getRouteByTitle(rs.getString("route")),
            tripCompositionRepository.getTripCompositionByTripId(rs.getLong("trip_id")),
            rs.getTimestamp("departure").toLocalDateTime(),
            rs.getBigDecimal("price"));

    public void addTrip(Trip trip) {
        KeyHolder key = new GeneratedKeyHolder();
        String sql = "INSERT INTO `trips` " +
                "(`route`, `departure`, `price`) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                final PreparedStatement ps = connection.prepareStatement(sql,
                        Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, trip.getRoute().getTitle());
                ps.setTimestamp(2, java.sql.Timestamp.valueOf(trip.getDeparture()));
                ps.setBigDecimal(3, trip.getPrice());
                return ps;
            }
        }, key);
        long tripId = key.getKey().longValue();
        trip.getTripComposition().forEach(tripComposition -> tripComposition.setTripId(tripId));
        tripCompositionRepository.addListTripComposition(trip.getTripComposition());
    }

    public Trip getTripById(long tripId) {
        String sql = "SELECT * " +
                "FROM `trips` " +
                "WHERE `trip_id` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, tripId);
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Trip> getTripsBetweenDates(LocalDate firstDate, LocalDate secondDate) {
        String sql = "SELECT * " +
                "FROM `trips` " +
                "WHERE `departure` BETWEEN ? and ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, firstDate, secondDate);
    }

    public List<Trip> getTripsByRouteTitleAndDepartureBetweenDateTimes(String title, LocalDateTime firstDateTime, LocalDateTime secondDateTime) {
        String sql = "SELECT * " +
                "FROM `trips` " +
                "WHERE `route` = ? AND `departure` BETWEEN ? and ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, title, firstDateTime, secondDateTime);
    }

}
