package com.epam.lab.rto.dto;

import com.epam.lab.rto.dto.enums.UserRole;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class User {

    private long id;

    @Email(message = "Введите валидный e-mail")
    @NotBlank(message = "Поле не должно быть пустым")
    private String email;

    @Size(min = 5, max = 18, message = "Пароль должен состоять из 5-18 символов")
    private String password;

    @NotBlank(message = "Поле не должно быть пустым")
    private String surname;

    @NotBlank(message = "Поле не должно быть пустым")
    private String name;

    private String patronymic;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;

    private boolean sex = true;
    private UserRole role;
}
