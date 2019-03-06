package com.epam.lab.rto.repository.interfaces;

import com.epam.lab.rto.dto.Trip;

import java.time.LocalDateTime;
import java.util.List;

public interface ITripRepository {
    Trip addTrip(Trip trip);

    Trip getTripById(long tripId);

    List<Trip> getTripsBetweenDates(LocalDateTime firstDate, LocalDateTime secondDate);

    List<Trip> getTripsByRouteTitleAndDepartureBetweenDateTimes(String title, LocalDateTime firstDateTime, LocalDateTime secondDateTime);
}