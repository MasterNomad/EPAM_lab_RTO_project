package com.epam.lab.rto.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Route {

    private String title;
    private List<Station> routeList;
    private DayOfWeek departureDay;
    private LocalTime departureTime;

    private int averageSpeed;

    private class Station {

        private String stationName;
        private int travelTime;
        private int stopDuration;

        public Station(String stationName, int travelTime, int stopDuration) {
            this.stationName = stationName;
            this.travelTime = travelTime;
            this.stopDuration = stopDuration;
        }

        private Station(String stationName) {
            this.stationName = stationName;
        }

        @Override
        public String toString() {
            return String.format("Станция: %s; Длительность остановки: %s минут\n",
                    stationName,
                    stopDuration);
        }
    }

    public Route(String title) {
        this.title = title;
        this.routeList = new ArrayList<>();
    }

    public Route(String title, List<String> routeList, DayOfWeek departureDay, LocalTime departureTime, int defaultStopDuration, int averageSpeed) {
        this(title);
        for (String stationName : routeList) {
            this.routeList.add(new Station(stationName));
        }
        setAverageSpeed(averageSpeed);
        setDepartureDayTime(departureDay, departureTime);
        setDefaultStopDuration(defaultStopDuration);
    }

    public void addStation(String stationName, int travelTime, int stopDuration) {
        routeList.add(new Station(stationName, travelTime, stopDuration));
    }


    public String getTitle() {
        return title;
    }

    public DayOfWeek getDepartureDay() {
        return departureDay;
    }

    public LocalTime getDepartureTime() {
        return departureTime;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public int length() {
        return routeList.size();
    }

    public String getStationName(int index) {
        return routeList.get(index).stationName;
    }

    public int getStationTravelTime(int index) {
        return routeList.get(index).travelTime;
    }

    public int getStationStopDuration(int index) {
        return routeList.get(index).stopDuration;
    }

    public int getStationTravelTime(int index, int travelTime) {
        return routeList.get(index).travelTime;
    }


    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setDepartureDayTime(DayOfWeek departureDay, LocalTime departureTime) {
        this.departureDay = departureDay;
        this.departureTime = departureTime;
    }

    public void setDefaultStopDuration(int minutes) {
        for (int i = 1; i < routeList.size(); i++) {
            routeList.get(i).stopDuration = minutes;
        }
    }

    public void setStationStopDuration(int index, int minutes) {
        routeList.get(index).stopDuration = minutes;
    }

    public void setStationTravelTime(int index, int travelTime) {
        routeList.get(index).travelTime = travelTime;
    }

    @Override
    public String toString() {
        return String.format("Мршрут: %s\n %s", title, routeList);
    }
}
