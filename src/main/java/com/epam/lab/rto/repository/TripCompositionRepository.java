package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.TripComposition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TripCompositionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CarriageRepository carriageRepository;

    private RowMapper<TripComposition> ROW_MAPPER = (rs, rowNum) -> new TripComposition(rs.getLong("trip_id"),
            carriageRepository.getCarriageById(rs.getLong("carriage_id")),
            rs.getInt("amount"),
            rs.getInt("places_sold"));

    public void addTripComposition(TripComposition tripComposition) {
        String sql = "INSERT INTO `trip_composition` " +
                "(`trip_id`, `carriage_id`, `amount`, `places_sold`) " +
                "VALUES (?, ?, ?, ?) ";

        jdbcTemplate.update(sql,
                tripComposition.getTripId(),
                tripComposition.getCarriage().getId(),
                tripComposition.getAmount(),
                tripComposition.getPlacesSold());
    }

    public void addListTripComposition(List<TripComposition> tripCompositions) {
        tripCompositions.forEach(this::addTripComposition);
    }

    public List<TripComposition> getAllTripComposition(long tripId) {
        String sql = "SELECT * " +
                "FROM `trip_composition` " +
                "WHERE `trip_id` = ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, tripId);
    }

    public List<TripComposition> getTripCompositionByTripId(long tripId) {
        String sql = "SELECT * " +
                "FROM `trip_composition` " +
                "WHERE `trip_id` = ?";

            return jdbcTemplate.query(sql, ROW_MAPPER, tripId);
    }

    public TripComposition getTripCompositionByTripIdAndCarriageId(long tripId, long carriageId) {
        String sql = "SELECT * " +
                "FROM `trip_composition` " +
                "WHERE `trip_id` = ? AND `carriage_id` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, tripId, carriageId);
        } catch (
                EmptyResultDataAccessException e) {
            return null;
        }
    }

    public void increaseSoldPlaceByTripIdAndCarriageId (long tripId, long carriageId) {
       String sql = "UPDATE `trip_composition` " +
                "SET `places_sold` = `places_sold` + 1 " +
                "WHERE `trip_id` = ? AND `carriage_id` = ?";
        jdbcTemplate.update(sql, tripId, carriageId);
    }

    public void decreaseSoldPlaceByTripIdAndCarriageId (long tripId, long carriageId) {
        String sql = "UPDATE `trip_composition` " +
                "SET `places_sold` = `places_sold` - 1 " +
                "WHERE `trip_id` = ? AND `carriage_id` = ?";
        jdbcTemplate.update(sql, tripId, carriageId);
    }
}
