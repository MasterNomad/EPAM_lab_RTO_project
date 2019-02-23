package com.epam.lab.rto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class Trip {

    private long id;
    private Route route;
    private Map <Carriage, Integer> carriages;
    private LocalDateTime departure;
    private BigDecimal price;

    public Trip() {
    }

    public Trip(long id, Route route, LocalDateTime departure, BigDecimal price) {
        this.id = id;
        this.route = route;
        this.departure = departure;
        this.price = price;
    }

    public Trip(Route route, Map<Carriage, Integer> carriages, LocalDateTime departure, BigDecimal price) {
        this.route = route;
        this.carriages = carriages;
        this.departure = departure;
        this.price = price;
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