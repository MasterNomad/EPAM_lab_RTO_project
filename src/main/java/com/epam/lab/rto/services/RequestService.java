package com.epam.lab.rto.services;

import com.epam.lab.rto.dto.Carriage;
import com.epam.lab.rto.dto.Request;
import com.epam.lab.rto.dto.Route;
import com.epam.lab.rto.dto.Trip;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

@Service
public class RequestService {

    @Autowired
    private TripService tripService;

    @Autowired
    private RouteService routeService;


    public List<Request> findTrains(String departureCity, String destinationCity, LocalDateTime departure) {
        List<Request> result = new ArrayList<>();

        List<Trip> trips = tripService.findTripsWithoutTransfer(departureCity, destinationCity, departure);
        for (Trip trip : trips) {
            LocalDateTime departureDateTime = getDepartureDateTime(trip, departureCity);
            LocalDateTime arrivalDateTime = getArrivalDateTime(trip, destinationCity);
            Carriage carriage = trip.getTripComposition().get(0).getCarriage();
            BigDecimal price = trip.getPrice().multiply(carriage.getPriceFactor()).round(new MathContext(3, RoundingMode.HALF_UP));
            result.add(new Request(trip, departureCity, departureDateTime, destinationCity, arrivalDateTime, carriage, price));
        }
        return result;
    }

    private LocalDateTime getDepartureDateTime(Trip trip, String departureCity) {
        LocalDateTime result;
        Route route = trip.getRoute();
        if (route.getStationStopDuration(departureCity) == -1) {
            result = trip.getDeparture();
        } else {
            result = trip.getDeparture()
                    .plus(route.getStationTravelTime(departureCity) + route.getStationStopDuration(departureCity), ChronoUnit.MINUTES);
        }
        return result;
    }

    private LocalDateTime getArrivalDateTime(Trip trip, String destinationCity) {
        return trip.getDeparture().plus(trip.getRoute().getStationTravelTime(destinationCity), ChronoUnit.MINUTES);
    }
}
