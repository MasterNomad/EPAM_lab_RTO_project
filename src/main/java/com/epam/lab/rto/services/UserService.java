package com.epam.lab.rto.services;

import com.epam.lab.rto.dao.UserRepository;
import com.epam.lab.rto.dto.User;
import com.epam.lab.rto.exceptions.NoSuchUserException;
import com.epam.lab.rto.exceptions.PasswordNotMatchException;
import com.epam.lab.rto.exceptions.SuchUserAlreadyExistException;
import com.epam.lab.rto.exceptions.WrongAgeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User getUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    public User registerUser(User user, String confirmPassword) {
        if (userRepository.getUserByEmail(user.getEmail()) != null) {
            throw new SuchUserAlreadyExistException("e-mail уже занят");
        }
        if (!user.getPassword().equals(confirmPassword)) {
            throw new PasswordNotMatchException("Пароли не совпадают");
        }
        if (user.getBirthDate().until(LocalDate.now(), ChronoUnit.YEARS) < 18) {
            throw new WrongAgeException("Регстрация возможна с 18 лет");
        }

        return userRepository.add(user);
    }

    public User enter(User user) {
        User currentUser = userRepository.getUserByEmail(user.getEmail());
        if (Objects.isNull(currentUser)) {
            throw new NoSuchUserException("Неверный логин или пароль");
        }
        if (!user.getPassword().equals(currentUser.getPassword())) {
            throw new PasswordNotMatchException("Неверный логин или пароль");
        }
        return currentUser;
    }
}
