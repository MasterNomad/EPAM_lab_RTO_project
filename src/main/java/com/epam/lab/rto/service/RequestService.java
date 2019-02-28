package com.epam.lab.rto.service;

import com.epam.lab.rto.dto.*;
import com.epam.lab.rto.repository.CarriageRepository;
import com.epam.lab.rto.repository.RequestRepository;
import org.apache.jasper.tagplugins.jstl.core.If;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RequestService {

    @Autowired
    private TripService tripService;

    @Autowired
    private RouteService routeService;

    @Autowired
    private RequestRepository requestRepository;

    // заменить
    @Autowired
    private CarriageRepository carriageRepository;

    public List<Request> findTrains(String departureCity, String destinationCity, LocalDateTime departure) {
        List<Request> result = new ArrayList<>();

        List<Trip> trips = tripService.findTripsWithoutTransfer(departureCity, destinationCity, departure);
        for (Trip trip : trips) {
            LocalDateTime departureDateTime = getDepartureDateTime(trip, departureCity);
            LocalDateTime arrivalDateTime = getArrivalDateTime(trip, destinationCity);
            Carriage carriage = trip.getTripComposition().get(0).getCarriage();
            BigDecimal price = trip.getPrice().multiply(carriage.getPriceFactor());
            result.add(new Request(trip, departureCity, departureDateTime, destinationCity, arrivalDateTime, carriage, price));
        }
        return result;
    }

    public void addRequest(long tripId, String departureCity, String destinationCity, String carriageName, User user) {
        Trip trip = tripService.getTripById(tripId);
        Carriage carriage = carriageRepository.getCarriageByName(carriageName);

        //Добавить проверки
        Request request = new Request(user, trip,
                departureCity, getDepartureDateTime(trip, departureCity),
                destinationCity, getArrivalDateTime(trip, destinationCity),
                carriage, tripService.getPriceByTripAndCarriage(trip, carriage),
                RequestStatus.UNPAID);
        requestRepository.addRequest(request);
        tripService.increaseSoldPlaceByRequest(request);
    }

    public List<Request> getActiveRequestsByUser(User user) {
        return requestRepository.getActiveRequestsByUserId(user.getId());
    }

    public List<Request> getInactiveRequestsByUser(User user) {
        return requestRepository.getInactiveRequestsByUserId(user.getId());
    }

    public void closeRequest(long requestId, User user) {
        Request request = requestRepository.getRequestById(requestId);
        if (!Objects.isNull(request) && user.equals(request.getUser())) {
            requestRepository.setRequestStatusById(requestId, RequestStatus.CANCELED);
            tripService.decreaseSoldPlaceByRequest(request);
        }
    }

    public void paidRequest(long requestId, User user) {
        Request request = requestRepository.getRequestById(requestId);
        if (!Objects.isNull(request) && user.equals(request.getUser())) {
            requestRepository.setRequestStatusById(requestId, RequestStatus.PAID);
        }
    }

    public void rejectRequest(long requestId, User user) {
        Request request = requestRepository.getRequestById(requestId);
        if (!Objects.isNull(request) && user.getRole().equals(UserRole.ADMIN)) {
            requestRepository.setRequestStatusById(requestId, RequestStatus.REJECTED);
            tripService.decreaseSoldPlaceByRequest(request);
        }
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