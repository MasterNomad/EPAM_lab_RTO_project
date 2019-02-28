package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class RequestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void addRequest(Request request) {
        String sql = "INSERT INTO `requests` " +
                "(`user_id`, `trip_id`, `departure_city`, `departure_datetime`, " +
                "`destination_city`, `arrival_datetime`, `carriage_id`, `price`, `request_status`) " +
                "VALUES (?, ?, ?, ?, ?, ?,?, ?, ?);";

        jdbcTemplate.update(sql, request.getUser().getId(),
                request.getTrip().getId(),
                request.getDepartureCity(),
                request.getDepartureDateTime(),
                request.getDestinationCity(),
                request.getArrivalDateTime(),
                request.getCarriage().getId(),
                request.getPrice(),
                request.getRequestStatus().toString());

        sql = "UPDATE `trip_composition` " +
                "SET `places_sold` = `places_sold` + 1 " +
                "WHERE `trip_id` = ? AND `carriage_id` = ?";

        jdbcTemplate.update(sql, request.getTrip().getId(),
                request.getCarriage().getId());
    }
}
