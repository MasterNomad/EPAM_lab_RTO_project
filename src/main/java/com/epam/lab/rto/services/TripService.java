package com.epam.lab.rto.services;

import com.epam.lab.rto.dto.Carriage;
import com.epam.lab.rto.dto.Route;
import com.epam.lab.rto.dto.Trip;
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

    public List <Trip> getTripsBetweenDates (LocalDate firstDate, LocalDate secondDate) {
       return tripRepository.getTripsBetweenDates(firstDate,secondDate);
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
            Route route = routeService.getRouteByTitle(routes[i]);
            Map<Carriage, Integer> carriagesMap = getCarriageMap(Arrays.asList(carriages).subList(i * carriageAmount, i * carriageAmount + carriageAmount));
            result.add(new Trip(route, carriagesMap, departures[i], prices[i]));
            if (repeats[i]>0) {
                while (departures[i].plus(repeats[i], ChronoUnit.DAYS).toLocalDate().isBefore(lastDate)){
                    departures[i] = departures[i].plus(repeats[i], ChronoUnit.DAYS);
                    result.add(new Trip(route, carriagesMap, departures[i], prices[i]));
                }
            }
        }
        addTrips(result);
    }

    private void addTrips(List<Trip> trips) {
        for (Trip trip : trips) {
            tripRepository.addTrip(trip);
        }
    }

    private Map<Carriage, Integer> getCarriageMap(List<Integer> carriages) {
        Map<Carriage, Integer> result = new HashMap<>();
        for (int i = 0; i < carriages.size(); i++) {
            if (carriages.get(i) > 0) {
                result.put(carriageRepository.getCarriageById(i + 1), carriages.get(i));
            }
        }
        return result;
    }
}
