package com.epam.lab.rto.service;

import com.epam.lab.rto.dto.Carriage;
import com.epam.lab.rto.dto.Route;
import com.epam.lab.rto.dto.Trip;
import com.epam.lab.rto.dto.TripComposition;
import com.epam.lab.rto.repository.CarriageRepository;
import com.epam.lab.rto.repository.TripRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class TripService {

    @Autowired
    private RouteService routeService;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private CarriageRepository carriageRepository;

    public Trip getTripById(long tripId) {
        return tripRepository.getTripById(tripId);
    }

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

    public List<Trip> getTripsBetweenDates(LocalDate firstDate, LocalDate secondDate) {
        return tripRepository.getTripsBetweenDates(firstDate, secondDate);
    }

    public List<Trip> getTripsByRouteAndDepartureDateTime(Route route, LocalDateTime departure) {
        LocalDateTime firstDateTime = departure.minus(12, ChronoUnit.HOURS);
        LocalDateTime secondDateTime = departure.plus(12, ChronoUnit.HOURS);
        return tripRepository.getTripsByRouteTitleAndDepartureBetweenDateTimes(route.getTitle(), firstDateTime, secondDateTime);
    }

    public List <String> getPriceAndCarriageDescriptionByTripIdAndCarriageName(long tripId, String carriageName){
        List <String> result = new ArrayList<>();
        Carriage carriage = carriageRepository.getCarriageByName(carriageName);
        if (Objects.isNull(carriage)){
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

    public BigDecimal getPriceByTripAndCarriage(Trip trip, Carriage carriage) {
        return trip.getPrice().multiply(carriage.getPriceFactor());
    }

    public void addSchedule(String[] routes,
                            LocalDateTime[] departures,
                            BigDecimal[] prices,
                            Integer[] carriages,
                            Long[] repeats,
                            LocalDate lastDate) {

        List<Trip> result = new ArrayList<>();
        int carriageAmount = carriages.length / routes.length;

        for (int i = 0; i < routes.length; i++) {
            Route route = new Route(routes[i]);
            List<TripComposition> tripComposition = buildTripComposition(Arrays.asList(carriages).subList(i * carriageAmount, i * carriageAmount + carriageAmount));
            result.add(new Trip(route, tripComposition, departures[i], prices[i]));
            if (repeats[i] > 0) {
                while (departures[i].plus(repeats[i], ChronoUnit.DAYS).toLocalDate().isBefore(lastDate)) {
                    departures[i] = departures[i].plus(repeats[i], ChronoUnit.DAYS);
                    result.add(new Trip(route, tripComposition, departures[i], prices[i]));
                }
            }
        }
        addTrips(result);
    }

    public boolean isTripContainsCarriageTypePlaces(Trip trip, Carriage carriage) {
        for (TripComposition currentCarriage : trip.getTripComposition()) {
            if (currentCarriage.getCarriage().equals(carriage)) {
                return currentCarriage.getPlacesSold() < currentCarriage.getAmount()*carriage.getPlaces();
            }
        }
        return false;
    }

    private void addTrips(List<Trip> trips) {
        for (Trip trip : trips) {
            tripRepository.addTrip(trip);
        }
    }

    private List<TripComposition> buildTripComposition(List<Integer> carriages) {
        List<TripComposition> result = new ArrayList<>();
        for (int i = 0; i < carriages.size(); i++) {
            if (carriages.get(i) > 0) {
                result.add(new TripComposition(new Carriage(i + 1), carriages.get(i)));
            }
        }
        return result;
    }
}
