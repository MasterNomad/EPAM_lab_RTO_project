package com.epam.lab.rto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Trip {

    private long id;
    private Route route;
    private LocalDateTime departure;
    private BigDecimal price;

    public Trip() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}