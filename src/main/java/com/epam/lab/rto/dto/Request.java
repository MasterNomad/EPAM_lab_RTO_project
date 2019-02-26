package com.epam.lab.rto.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

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

    public Request() {
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Trip getTrip() {
        return trip;
    }

    public void setTrip(Trip trip) {
        this.trip = trip;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public LocalDateTime getDepartureDateTime() {
        return departureDateTime;
    }

    public void setDepartureDateTime(LocalDateTime departureDateTime) {
        this.departureDateTime = departureDateTime;
    }

    public String getDestinationCity() {
        return destinationCity;
    }

    public void setDestinationCity(String destinationCity) {
        this.destinationCity = destinationCity;
    }

    public LocalDateTime getArrivalDateTime() {
        return arrivalDateTime;
    }

    public void setArrivalDateTime(LocalDateTime arrivalDateTime) {
        this.arrivalDateTime = arrivalDateTime;
    }

    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public RequestStatus getRequestStatus() {
        return requestStatus;
    }

    public void setRequestStatus(RequestStatus requestStatus) {
        this.requestStatus = requestStatus;
    }
}
