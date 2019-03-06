package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.Request;
import com.epam.lab.rto.dto.RequestStatus;
import com.epam.lab.rto.repository.interfaces.ICarriageRepository;
import com.epam.lab.rto.repository.interfaces.IRequestRepository;
import com.epam.lab.rto.repository.interfaces.ITripRepository;
import com.epam.lab.rto.repository.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class RequestRepository implements IRequestRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private ITripRepository tripRepository;

    @Autowired
    private ICarriageRepository carriageRepository;

    private RowMapper<Request> ROW_MAPPER = (rs, rowNum) -> new Request(rs.getLong("request_id"),
            userRepository.getUserById(rs.getLong("user_id")),
            tripRepository.getTripById(rs.getLong("trip_id")),
            rs.getString("departure_city"),
            rs.getTimestamp("departure_datetime").toLocalDateTime(),
            rs.getString("destination_city"),
            rs.getTimestamp("arrival_datetime").toLocalDateTime(),
            carriageRepository.getCarriageById(rs.getLong("carriage_id")),
            rs.getBigDecimal("price"),
            RequestStatus.valueOf(rs.getString("request_status")),
            rs.getBoolean("payment_state"));

    @Override
    public Request addRequest(Request request) {
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
        return request;
    }

    @Override
    public Request getRequestById(long requestId) {
        String sql = "SELECT * " +
                "FROM `requests` " +
                "WHERE `request_id` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, requestId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<Request> getActiveRequestsByUserId(long userId) {
        String sql = "SELECT * " +
                "FROM `requests` " +
                "WHERE `user_id` = ? AND `arrival_datetime` > ? AND `request_status` IN ('ACTIVE')";
        return jdbcTemplate.query(sql, ROW_MAPPER, userId, LocalDateTime.now());
    }

    @Override
    public List<Request> getInactiveRequestsByUserId(long userId) {
        String sql = "SELECT * " +
                "FROM `requests` " +
                "WHERE `user_id` = ? AND `arrival_datetime` < ? OR `request_status` IN ('REJECTED','CANCELED')";
        return jdbcTemplate.query(sql, ROW_MAPPER, userId, LocalDateTime.now());
    }

    @Override
    public List<Request> getInactiveRequestsBetweenDates(LocalDateTime firstDate, LocalDateTime secondDate) {
        String sql = "SELECT * " +
                "FROM (SELECT *" +
                "FROM `requests` " +
                "WHERE `departure_datetime` BETWEEN ? AND ?) AS T " +
                "WHERE `arrival_datetime` < ? OR `request_status` IN ('REJECTED','CANCELED')";
        return jdbcTemplate.query(sql, ROW_MAPPER, firstDate, secondDate, LocalDate.now());
    }

    @Override
    public List<Request> getActiveRequestsBetweenDates(LocalDateTime firstDate, LocalDateTime secondDate) {
        String sql = "SELECT * " +
                "FROM `requests` " +
                "WHERE (`departure_datetime` BETWEEN ? AND ?) AND `request_status` IN ('ACTIVE')";
        return jdbcTemplate.query(sql, ROW_MAPPER, firstDate, secondDate);
    }

    @Override
    public int setRequestStatusById(long requestId, RequestStatus status) {
        String sql = " UPDATE `requests` " +
                "SET `request_status` = ? " +
                "WHERE (`request_id` = ?)";

        return jdbcTemplate.update(sql, status.toString(), requestId);
    }

    @Override
    public int setRequestPaymentStateById(long requestId, boolean paid) {
        String sql = " UPDATE `requests` " +
                "SET `payment_state` = ? " +
                "WHERE (`request_id` = ?)";

        return jdbcTemplate.update(sql, paid, requestId);
    }
}