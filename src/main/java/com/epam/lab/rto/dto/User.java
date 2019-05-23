package com.epam.lab.rto.dto;

import com.epam.lab.rto.dto.enums.UserRole;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.Objects;

@Data
public class User {

    @PositiveOrZero
    private long id;

    @Email
    @NotBlank
    private String email;

    @Size(min = 5, max = 18)
    private String password;

    @NotBlank
    private String surname;

    @NotBlank
    private String name;

    private String patronymic;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private LocalDate birthDate;
    private boolean sex;
    private UserRole role;
}
