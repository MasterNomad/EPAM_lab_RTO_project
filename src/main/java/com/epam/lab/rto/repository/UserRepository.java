package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.User;
import com.epam.lab.rto.repository.interfaces.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepository implements IUserRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<User> ROW_MAPPER = new BeanPropertyRowMapper<>(User.class);

    @Override
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
                user.isSex());
        return user;
    }

    @Override
    public User getUserById(long userId) {
        String sql = "SELECT * " +
                "FROM `users` " +
                "WHERE `id` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, userId);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT `id`, `email`, `password` , `surname`, `name`, `patronymic`, `birthDate`, `sex`, `role` " +
                "FROM `users` " +
                "WHERE `email` = ?";
        try {
            return jdbcTemplate.queryForObject(sql, ROW_MAPPER, email);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
}