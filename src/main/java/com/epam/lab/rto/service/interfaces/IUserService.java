package com.epam.lab.rto.service.interfaces;

import com.epam.lab.rto.dto.User;

public interface IUserService {

    User registerUser(User user, String confirmPassword);

}