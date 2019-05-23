package com.epam.lab.rto.dto;

import com.epam.lab.rto.dto.enums.RequestStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Request {

    private long id;
    private User user;
    private Trip trip;
    private String departureCity;
    private LocalDateTime departureDateTime;
    private String destinationCity;
    private LocalDateTime arrivalDateTime;
    private Carriage carriage;
    private BigDecimal price;
    private RequestStatus requestStatus;
    private boolean paymentState = false;

    public Request(long id, User user, Trip trip, String departureCity,
                   LocalDateTime departureDateTime, String destinationCity,
                   LocalDateTime arrivalDateTime, Carriage carriage, BigDecimal price,
                   RequestStatus requestStatus, boolean paymentState) {
        this.id = id;
        this.user = user;
        this.trip = trip;
        this.departureCity = departureCity;
        this.departureDateTime = departureDateTime;
        this.destinationCity = destinationCity;
        this.arrivalDateTime = arrivalDateTime;
        this.carriage = carriage;
        this.price = price;
        this.requestStatus = requestStatus;
        this.paymentState = paymentState;
    }

    public Request(Trip trip, String departureCity, LocalDateTime departureDateTime,
                   String destinationCity, LocalDateTime arrivalDateTime, Carriage carriage, BigDecimal price) {
        this.trip = trip;
        this.departureCity = departureCity;
        this.departureDateTime = departureDateTime;
        this.destinationCity = destinationCity;
        this.arrivalDateTime = arrivalDateTime;
        this.carriage = carriage;
        this.price = price;
    }

    public Request(User user, Trip trip, String departureCity, LocalDateTime departureDateTime, String destinationCity,
                   LocalDateTime arrivalDateTime, Carriage carriage, BigDecimal price, RequestStatus requestStatus) {
        this.user = user;
        this.trip = trip;
        this.departureCity = departureCity;
        this.departureDateTime = departureDateTime;
        this.destinationCity = destinationCity;
        this.arrivalDateTime = arrivalDateTime;
        this.carriage = carriage;
        this.price = price;
        this.requestStatus = requestStatus;
    }
}
