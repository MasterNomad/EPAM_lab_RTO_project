package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.Request;
import com.epam.lab.rto.dto.RequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class RequestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CarriageRepository carriageRepository;

    private RowMapper<Request> ROW_MAPPER = (rs, rowNum) -> new Request(rs.getLong("request_id"),
            userRepository.getUserById(rs.getLong("user_id")),
            tripRepository.getTripById(rs.getLong("trip_id")),
            rs.getString("departure_city"),
            rs.getTimestamp("departure_datetime").toLocalDateTime(),
            rs.getString("destination_city"),
            rs.getTimestamp("arrival_datetime").toLocalDateTime(),
            carriageRepository.getCarriageById(rs.getLong("carriage_id")),
            rs.getBigDecimal("price"),
            RequestStatus.valueOf(rs.getString("request_status")));

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
    }

    public Request getRequestById(long requestId) {
        String sql = "SELECT * " +
                "FROM `requests` " +
                "WHERE `requests_id` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, requestId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Request> getActiveRequestsByUserId(long userId) {
        String sql = "SELECT * " +
                "FROM `requests` " +
                "WHERE `user_id` = ? AND `arrival_datetime` > ? AND `request_status` IN ('UNPAID','PAID')";
        return jdbcTemplate.query(sql, ROW_MAPPER, userId, LocalDateTime.now());
    }

    public List<Request> getInactiveRequestsByUserId(long userId) {
        String sql = "SELECT * " +
                "FROM `requests` " +
                "WHERE `user_id` = ? AND `arrival_datetime` < ? OR `request_status` IN ('REJECTED','CANCELED')";
        return jdbcTemplate.query(sql, ROW_MAPPER, userId, LocalDateTime.now());
    }

    public int setRequestStatusById(long requestId, RequestStatus status) {
        String sql = " UPDATE `requests` " +
                "SET `request_status` = ? " +
                "WHERE `request_id` = ?";
        return jdbcTemplate.update(sql, status.toString(), requestId);
    }
}
