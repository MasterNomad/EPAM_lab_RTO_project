package com.epam.lab.rto.service.interfaces;

import com.epam.lab.rto.dto.Carriage;
import com.epam.lab.rto.dto.Request;
import com.epam.lab.rto.dto.Trip;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ITripService {
    Trip getTripById(long tripId);

    List<Trip> findTripsWithoutTransfer(String startStation, String finishStation, LocalDateTime departure);

    List<Trip> getTripsBetweenDates(LocalDate firstDate, LocalDate secondDate);

    List<String> getPriceAndCarriageDescriptionByTripIdAndCarriageName(long tripId, String carriageName);

    List<Trip> createSchedule(String[] routes, LocalDateTime[] departures, BigDecimal[] prices, Integer[] carriages,
                              Long[] repeats, LocalDate lastDate);

    void addSchedule(List<Trip> schedule);

    void increaseSoldPlaceByRequest(Request request);

    void decreaseSoldPlaceByRequest(Request request);

    BigDecimal getPriceByTripAndCarriage(Trip trip, Carriage carriage);

    boolean isTripContainsCarriageTypePlaces(Trip trip, Carriage carriage);
}