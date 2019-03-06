package com.epam.lab.rto.repository.interfaces;

import com.epam.lab.rto.dto.User;

public interface IUserRepository {

    User add(User user);

    User getUserById(long userId);

    User getUserByEmailAndPassword(String email, String password);

    User getUserByEmail(String email);
}