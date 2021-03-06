package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.Carriage;
import com.epam.lab.rto.repository.interfaces.ICarriageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarriageRepository implements ICarriageRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Carriage> ROW_MAPPER = new BeanPropertyRowMapper<>(Carriage.class);

    @Override
    public List<Carriage> getAllCarriages() {
        String sql = "SELECT * " +
                "FROM `carriage_types` " +
                "ORDER BY `id`";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public Carriage getCarriageById(long id) {
        String sql = "SELECT * " +
                "FROM `carriage_types` " +
                "WHERE `id` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Carriage getCarriageByName(String carriage) {
        String sql = "SELECT * " +
                "FROM `carriage_types`" +
                "WHERE `name` = ? ";
        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, carriage);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}