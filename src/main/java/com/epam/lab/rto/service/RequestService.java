package com.epam.lab.rto.service;

import com.epam.lab.rto.dto.*;
import com.epam.lab.rto.dto.enums.RequestStatus;
import com.epam.lab.rto.dto.enums.UserRole;
import com.epam.lab.rto.exceptions.AllPlacesSoldException;
import com.epam.lab.rto.repository.interfaces.IRequestRepository;
import com.epam.lab.rto.service.interfaces.IRequestService;
import com.epam.lab.rto.service.interfaces.ITrainService;
import com.epam.lab.rto.service.interfaces.ITripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class RequestService implements IRequestService {

    @Autowired
    private ITripService tripService;

    @Autowired
    private ITrainService trainService;

    @Autowired
    private IRequestRepository requestRepository;


    @Override
    public List<Request> createRequests(String departureCity, String destinationCity, LocalDateTime departureTime) {
        List<Request> result = new ArrayList<>();

        List<Trip> trips = tripService.findTripsWithoutTransfer(departureCity, destinationCity, departureTime);
        for (Trip trip : trips) {
            LocalDateTime departureDateTime = getDepartureDateTime(trip, departureCity);
            LocalDateTime arrivalDateTime = getArrivalDateTime(trip, destinationCity);
            Carriage carriage = trip.getTrainComposition().get(0).getCarriage();
            BigDecimal price = trip.getPrice().multiply(carriage.getPriceFactor());
            result.add(new Request(trip, departureCity, departureDateTime, destinationCity, arrivalDateTime, carriage, price));
        }
        return result;
    }

    @Override
    public Request createRequest(long tripId, String departureCity, String destinationCity, String carriageName, User user) {
        Trip trip = tripService.getTripById(tripId);
        Carriage carriage = trainService.getCarriageByName(carriageName);
        return new Request(user, trip,
                departureCity, getDepartureDateTime(trip, departureCity),
                destinationCity, getArrivalDateTime(trip, destinationCity),
                carriage, tripService.getPriceByTripAndCarriage(trip, carriage),
                RequestStatus.ACTIVE);
    }

    @Override
    public synchronized Request addRequest(Request request) {
        if (tripService.isTripContainsCarriageTypePlaces(request.getTrip(), request.getCarriage())) {
            requestRepository.addRequest(request);
            tripService.increaseSoldPlaceByRequest(request);
        } else {
            throw new AllPlacesSoldException();
        }
        return request;
    }

    @Override
    public List<Request> getActiveRequestsByUser(User user) {
        return requestRepository.getActiveRequestsByUserId(user.getId());
    }

    @Override
    public List<Request> getInactiveRequestsByUser(User user) {
        return requestRepository.getInactiveRequestsByUserId(user.getId());
    }

    @Override
    public List<Request> getActiveRequestsBetweenDates(LocalDate firstDate, LocalDate secondDate) {
        if (firstDate.isBefore(LocalDate.now())) {
            firstDate = LocalDate.now();
        }
        return requestRepository.getActiveRequestsBetweenDates(LocalDateTime.of(firstDate, LocalTime.of(4, 0)), LocalDateTime.of(secondDate.plusDays(1), LocalTime.of(4, 0)));
    }

    @Override
    public List<Request> getInactiveRequestsBetweenDates(LocalDate firstDate, LocalDate secondDate) {
        return requestRepository.getInactiveRequestsBetweenDates(LocalDateTime.of(firstDate, LocalTime.of(4, 0)), LocalDateTime.of(secondDate.plusDays(1), LocalTime.of(4, 0)));
    }

    @Override
    public boolean cancelRequest(long requestId, User user) {
        Request request = requestRepository.getRequestById(requestId);
        if (!Objects.isNull(request) && user.equals(request.getUser())) {
            requestRepository.setRequestStatusById(requestId, RequestStatus.CANCELED);
            tripService.decreaseSoldPlaceByRequest(request);
            return true;
        }
        return false;
    }

    @Override
    public boolean paidRequest(long requestId, User user) {
        Request request = requestRepository.getRequestById(requestId);
        if (!Objects.isNull(request) && user.equals(request.getUser())) {
            return requestRepository.setRequestPaymentStateById(requestId, true) > 0;
        }
        return false;
    }

    @Override
    public boolean rejectRequest(long requestId, User user) {
        Request request = requestRepository.getRequestById(requestId);
        if (!Objects.isNull(request) && user.getRole().equals(UserRole.ADMIN)) {
            requestRepository.setRequestStatusById(requestId, RequestStatus.REJECTED);
            tripService.decreaseSoldPlaceByRequest(request);
            return true;
        }
        return false;
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