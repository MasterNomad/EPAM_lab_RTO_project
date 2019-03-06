package com.epam.lab.rto.service;

import com.epam.lab.rto.dto.*;
import com.epam.lab.rto.repository.interfaces.ITripRepository;
import com.epam.lab.rto.service.interfaces.IRouteService;
import com.epam.lab.rto.service.interfaces.ITrainService;
import com.epam.lab.rto.service.interfaces.ITripService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TripService implements ITripService {

    @Autowired
    private IRouteService routeService;

    @Autowired
    private ITrainService trainService;

    @Autowired
    private ITripRepository tripRepository;


    @Override
    public Trip getTripById(long tripId) {
        return tripRepository.getTripById(tripId);
    }

    @Override
    public List<Trip> findTripsWithoutTransfer(String startStation, String finishStation, LocalDateTime departure) {
        List<Trip> result = new ArrayList<>();

        List<Route> routes = routeService.findRoutesBetweenStations(startStation, finishStation);
        for (Route route : routes) {
            LocalDateTime tripDeparture = departure.minus(
                    route.getStationTravelTime(startStation) + route.getStationStopDuration(startStation),
                    ChronoUnit.MINUTES);
            result.addAll(getTripsByRouteAndDepartureDateTime(route, tripDeparture));
        }
        return result;
    }

    @Override
    public List<Trip> getTripsBetweenDates(LocalDate firstDate, LocalDate secondDate) {
        return tripRepository.getTripsBetweenDates(LocalDateTime.of(firstDate, LocalTime.of(4, 0)), LocalDateTime.of(secondDate.plusDays(1), LocalTime.of(4, 0)));
    }

    @Override
    public List<Trip> createSchedule(String[] routes,
                                     LocalDateTime[] departures,
                                     BigDecimal[] prices,
                                     Integer[] carriages,
                                     Long[] repeats,
                                     LocalDate lastDate) {
        List<Trip> result = new ArrayList<>();
        int carriageAmount = carriages.length / routes.length;

        for (int i = 0; i < routes.length; i++) {
            Route route = new Route(routes[i]);
            List<TrainComposition> trainComposition = buildTripComposition(Arrays.asList(carriages).subList(i * carriageAmount, i * carriageAmount + carriageAmount));
            result.add(new Trip(route, trainComposition, departures[i], prices[i]));
            if (repeats[i] > 0) {
                while (departures[i].plus(repeats[i], ChronoUnit.DAYS).toLocalDate().isBefore(lastDate)) {
                    departures[i] = departures[i].plus(repeats[i], ChronoUnit.DAYS);
                    result.add(new Trip(route, trainComposition, departures[i], prices[i]));
                }
            }
        }
        return result;
    }

    @Override
    public void addSchedule(List<Trip> schedule) {
        for (Trip trip : schedule) {
            tripRepository.addTrip(trip);
        }
    }

    @Override
    public List<String> getPriceAndCarriageDescriptionByTripIdAndCarriageName(long tripId, String carriageName) {
        List<String> result = new ArrayList<>();
        Carriage carriage = trainService.getCarriageByName(carriageName);
        if (Objects.isNull(carriage)) {
            result.add("В рейсе отсутствует вагон указанного типа");
            result.add("-");
            return result;
        }
        Trip trip = tripRepository.getTripById(tripId);
        if (isTripContainsCarriageTypePlaces(trip, carriage)) {
            result.add(carriage.getDescription());
            result.add(getPriceByTripAndCarriage(trip, carriage).toString());
            return result;
        } else {
            result.add("В рейсе отсутствует вагон указанного типа или все места проданны");
            result.add("-");
            return result;
        }
    }

    @Override
    public BigDecimal getPriceByTripAndCarriage(Trip trip, Carriage carriage) {
        return trip.getPrice().multiply(carriage.getPriceFactor());
    }

    @Override
    public void increaseSoldPlaceByRequest(Request request) {
        trainService.increaseSoldPlaceByTripIdAndCarriageId(
                request.getTrip().getId(),
                request.getCarriage().getId()
        );
    }

    @Override
    public void decreaseSoldPlaceByRequest(Request request) {
        trainService.decreaseSoldPlaceByTripIdAndCarriageId(
                request.getTrip().getId(),
                request.getCarriage().getId()
        );
    }


    private List<Trip> getTripsByRouteAndDepartureDateTime(Route route, LocalDateTime departure) {
        LocalDateTime firstDateTime = departure.minus(12, ChronoUnit.HOURS);
        LocalDateTime secondDateTime = departure.plus(12, ChronoUnit.HOURS);
        return tripRepository.getTripsByRouteTitleAndDepartureBetweenDateTimes(route.getTitle(), firstDateTime, secondDateTime);
    }

    private boolean isTripContainsCarriageTypePlaces(Trip trip, Carriage carriage) {
        for (TrainComposition currentCarriage : trip.getTrainComposition()) {
            if (currentCarriage.getCarriage().equals(carriage)) {
                return currentCarriage.getPlacesSold() < currentCarriage.getAmount() * carriage.getPlaces();
            }
        }
        return false;
    }

    private List<TrainComposition> buildTripComposition(List<Integer> carriages) {
        List<TrainComposition> result = new ArrayList<>();
        for (int i = 0; i < carriages.size(); i++) {
            if (carriages.get(i) > 0) {
                result.add(new TrainComposition(new Carriage(i + 1), carriages.get(i)));
            }
        }
        return result;
    }
}