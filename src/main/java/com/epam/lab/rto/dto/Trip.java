package com.epam.lab.rto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Trip {

    private long id;
    private Route route;
    private List<CarriageComposition> TrainComposition;
    private LocalDateTime departure;
    private BigDecimal price;

    public Trip(long id, Route route, List<CarriageComposition> TrainComposition, LocalDateTime departure, BigDecimal price) {
        this.id = id;
        this.route = route;
        this.TrainComposition = TrainComposition;
        this.departure = departure;
        this.price = price;
    }

    public Trip(Route route, List<CarriageComposition> TrainComposition, LocalDateTime departure, BigDecimal price) {
        this.route = route;
        this.TrainComposition = TrainComposition;
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

    public List<CarriageComposition> getTrainComposition() {
        return TrainComposition;
    }

    public void setTrainComposition(List<CarriageComposition> trainComposition) {
        this.TrainComposition = trainComposition;
    }
}