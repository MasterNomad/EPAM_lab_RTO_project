package com.epam.lab.rto.dao;

import com.epam.lab.rto.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

    public User add(User user) {
        String sql = "INSERT INTO `users` " +
                "(`email`, `password`, `surname`, `name`, `patronymic`, `birthDate`, `sex`) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                user.getEmail().trim(),
                user.getPassword().trim(),
                user.getSurname().trim(),
                user.getName().trim(),
                user.getPatronymic().trim(),
                user.getBirthDate(),
                user.getSex());
        return user;
    }

    public User getUserByEmail(String email) {
        String sql = "SELECT * " +
                "FROM `users` " +
                "WHERE email = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

}
