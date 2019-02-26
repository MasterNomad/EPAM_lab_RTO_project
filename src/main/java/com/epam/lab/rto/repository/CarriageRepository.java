package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.Carriage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarriageRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    private RowMapper<Carriage> ROW_MAPPER = new BeanPropertyRowMapper<>(Carriage.class);

    public List<Carriage> getAll() {
        String sql = "SELECT * " +
                "FROM `carriage_types` " +
                "ORDER BY `id`";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

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

    public Carriage getCarriageByName(String carriage) {
        String sql = "SELECT * " +
                "FROM `carriage_types`" +
                "WHERE `name` = ? ";
        return jdbcTemplate.queryForObject(sql, ROW_MAPPER, carriage);
    }
}
