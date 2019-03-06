package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.Locomotive;
import com.epam.lab.rto.repository.interfaces.ILocomotiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class LocomotiveRepository implements ILocomotiveRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<Locomotive> ROW_MAPPER = new BeanPropertyRowMapper<>(Locomotive.class);

    @Override
    public List<Locomotive> getAllLocomotives() {
        String sql = "SELECT * " +
                "FROM locomotives";
        return jdbcTemplate.query(sql, ROW_MAPPER);
    }

    @Override
    public Locomotive getLocomotiveById(long id) {
        String sql = "SELECT * " +
                "FROM locomotives " +
                "WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public Locomotive getLocomotiveByName(String name) {
        String sql = "SELECT * " +
                "FROM locomotives " +
                "WHERE name = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, name);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}