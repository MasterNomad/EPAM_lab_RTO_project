package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.CarriageComposition;
import com.epam.lab.rto.repository.interfaces.ICarriageRepository;
import com.epam.lab.rto.repository.interfaces.ITrainCompositionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TrainCompositionRepository implements ITrainCompositionRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ICarriageRepository carriageRepository;

    private RowMapper<CarriageComposition> ROW_MAPPER = (rs, rowNum) -> new CarriageComposition(rs.getLong("trip_id"),
            carriageRepository.getCarriageById(rs.getLong("carriage_id")),
            rs.getInt("amount"),
            rs.getInt("places_sold"));

    @Override
    public CarriageComposition addTrainComposition(CarriageComposition carriageComposition) {
        String sql = "INSERT INTO `trip_composition` " +
                "(`trip_id`, `carriage_id`, `amount`, `places_sold`) " +
                "VALUES (?, ?, ?, ?) ";

        jdbcTemplate.update(sql,
                carriageComposition.getTripId(),
                carriageComposition.getCarriage().getId(),
                carriageComposition.getAmount(),
                carriageComposition.getPlacesSold());

        return carriageComposition;
    }

    @Override
    public List<CarriageComposition> addTrainCompositionList(List<CarriageComposition> carriageCompositions) {
        carriageCompositions.forEach(this::addTrainComposition);
        return carriageCompositions;
    }

    @Override
    public List<CarriageComposition> getTrainCompositionByTripId(long tripId) {
        String sql = "SELECT * " +
                "FROM `trip_composition` " +
                "WHERE `trip_id` = ?";
        return jdbcTemplate.query(sql, ROW_MAPPER, tripId);
    }

    @Override
    public int increaseSoldPlaceByTripIdAndCarriageId(long tripId, long carriageId) {
        String sql = "UPDATE `trip_composition` " +
                "SET `places_sold` = `places_sold` + 1 " +
                "WHERE `trip_id` = ? AND `carriage_id` = ?";
        return jdbcTemplate.update(sql, tripId, carriageId);
    }

    @Override
    public int decreaseSoldPlaceByTripIdAndCarriageId(long tripId, long carriageId) {
        String sql = "UPDATE `trip_composition` " +
                "SET `places_sold` = `places_sold` - 1 " +
                "WHERE `trip_id` = ? AND `carriage_id` = ?";
        return jdbcTemplate.update(sql, tripId, carriageId);
    }
}