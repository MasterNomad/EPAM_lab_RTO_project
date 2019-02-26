package com.epam.lab.rto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Trip {

    private long id;
    private Route route;
    private List<TripComposition> tripComposition;
    private LocalDateTime departure;
    private BigDecimal price;

    public Trip() {
    }

    public Trip(long id, Route route, List<TripComposition> tripComposition, LocalDateTime departure, BigDecimal price) {
        this.id = id;
        this.route = route;
        this.tripComposition = tripComposition;
        this.departure = departure;
        this.price = price;
    }

    public Trip(Route route, List<TripComposition> tripComposition, LocalDateTime departure, BigDecimal price) {
        this.route = route;
        this.tripComposition = tripComposition;
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

    public List<TripComposition> getTripComposition() {
        return tripComposition;
    }

    public void setTripComposition(List<TripComposition> tripComposition) {
        this.tripComposition = tripComposition;
    }
}