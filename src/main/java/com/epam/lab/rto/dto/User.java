package com.epam.lab.rto.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private long id;
    private String email;
    private String password;
    private String surname;
    private String name;
    private String patronymic;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    private boolean sex;
    private UserRole role;

    public User() {
    }

    public User(long id, String email, String password, String surname, String name, String patronymic, LocalDate birthDate, boolean sex, UserRole role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.birthDate = birthDate;
        this.sex = sex;
        this.role = role;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return getId() == user.getId() &&
                getSex() == user.getSex() &&
                getEmail().equals(user.getEmail()) &&
                getSurname().equals(user.getSurname()) &&
                getName().equals(user.getName()) &&
                getPatronymic().equals(user.getPatronymic()) &&
                getBirthDate().equals(user.getBirthDate()) &&
                getRole() == user.getRole();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getEmail(), getSurname(), getName(), getPatronymic(), getBirthDate(), getSex(), getRole());
    }
}
