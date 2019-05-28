package com.epam.lab.rto.repository.interfaces;

import com.epam.lab.rto.dto.User;

public interface IUserRepository {

    User add(User user);

    User getUserById(long userId);

    User getUserByEmail(String email);
}