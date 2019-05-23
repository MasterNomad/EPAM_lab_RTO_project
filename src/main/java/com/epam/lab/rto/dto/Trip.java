package com.epam.lab.rto.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
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

}