package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.Trip;
import com.epam.lab.rto.repository.interfaces.IRouteRepository;
import com.epam.lab.rto.repository.interfaces.ITrainCompositionRepository;
import com.epam.lab.rto.repository.interfaces.ITripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class TripRepository implements ITripRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IRouteRepository routeRepository;

    @Autowired
    private ITrainCompositionRepository tripCompositionRepository;

    private RowMapper<Trip> ROW_MAPPER = (rs, rowNum) -> new Trip(rs.getLong("trip_id"),
            routeRepository.getRouteByTitle(rs.getString("route")),
            tripCompositionRepository.getTrainCompositionByTripId(rs.getLong("trip_id")),
            rs.getTimestamp("departure").toLocalDateTime(),
            rs.getBigDecimal("price"));

    @Override
    public Trip addTrip(Trip trip) {
        KeyHolder key = new GeneratedKeyHolder();
        String sql = "INSERT INTO `trips` " +
                "(`route`, `departure`, `price`) " +
                "VALUES (?, ?, ?)";
        jdbcTemplate.update(connection -> {
            final PreparedStatement ps = connection.prepareStatement(sql,
                    Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, trip.getRoute().getTitle());
            ps.setTimestamp(2, java.sql.Timestamp.valueOf(trip.getDeparture()));
            ps.setBigDecimal(3, trip.getPrice());
            return ps;
        }, key);
        long tripId = Objects.requireNonNull(key.getKey()).longValue();
        trip.getTrainComposition().forEach(tripComposition -> tripComposition.setTripId(tripId));
        tripCompositionRepository.addTrainCompositionList(trip.getTrainComposition());
        return trip;
    }

    @Override
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

    @Override
    public List<Trip> getTripsBetweenDates(LocalDateTime firstDate, LocalDateTime secondDate) {
        String sql = "SELECT * " +
                "FROM `trips` " +
                "WHERE `departure` BETWEEN ? and ?" +
                "ORDER BY `departure`";
        return jdbcTemplate.query(sql, ROW_MAPPER, firstDate, secondDate);
    }


    @Override
    public List<Trip> getTripsByRouteTitleAndDepartureBetweenDateTimes(String title, LocalDateTime firstDateTime, LocalDateTime secondDateTime) {
        String sql = "SELECT * " +
                "FROM `trips` " +
                "WHERE `route` = ? AND `departure` BETWEEN ? and ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, title, firstDateTime, secondDateTime);
    }
}