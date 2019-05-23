package com.epam.lab.rto.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.Objects;

@Data
public class Carriage {

    private int id;
    private String name;
    private String description;
    private int places;
    private BigDecimal priceFactor;

    public Carriage() {
    }

    public Carriage(int id) {
        this.id = id;
    }
}
