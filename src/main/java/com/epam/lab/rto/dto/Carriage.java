package com.epam.lab.rto.dto;

import java.math.BigDecimal;
import java.util.Objects;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPlaces() {
        return places;
    }

    public void setPlaces(int places) {
        this.places = places;
    }

    public BigDecimal getPriceFactor() {
        return priceFactor;
    }

    public void setPriceFactor(BigDecimal priceFactor) {
        this.priceFactor = priceFactor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Carriage carriage = (Carriage) o;
        return getId() == carriage.getId() &&
                getPlaces() == carriage.getPlaces() &&
                getName().equals(carriage.getName()) &&
                getDescription().equals(carriage.getDescription()) &&
                getPriceFactor().equals(carriage.getPriceFactor());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getDescription(), getPlaces(), getPriceFactor());
    }
}
