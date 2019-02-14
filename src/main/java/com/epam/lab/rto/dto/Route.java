package com.epam.lab.rto.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Route {

    private String title;

    private List<Station> routeList;

    private int averageSpeed;

    private class Station {

        private String stationName;
        private DayOfWeek arrivalDay;
        private LocalTime arrivalTime;
        private int stopDuration;

        public Station(String stationName, DayOfWeek arrivalDay, LocalTime arrivalTime, int stopDuration) {
            this.stationName = stationName;
            this.arrivalDay = arrivalDay;
            this.arrivalTime = arrivalTime;
            this.stopDuration = stopDuration;
        }

        private Station(String stationName) {
            this.stationName = stationName;
        }

        @Override
        public String toString() {
            return String.format("Станция: %s; День прибытия: %s; Время прибытия: %s; Длительность остановки: %s минут\n",
                    stationName,
                    arrivalDay,
                    arrivalTime,
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

    public String getTitle() {
        return title;
    }

    public DayOfWeek getDepartureDay() {
        return getStationArrivalDay(0);
    }

    public LocalTime getDepartureTime() {
        return getStationArrivalTime(0);
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

    public boolean isBefor(String firstStation, String secondStation) {
        for (int i = 0; i < routeList.size(); i++) {
            if (routeList.get(i).stationName.equals(firstStation)) {
                for (int j = i; j < routeList.size(); j++) {
                    if (routeList.get(j).stationName.equals(secondStation)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public DayOfWeek getStationArrivalDay(int index) {
        return routeList.get(index).arrivalDay;
    }

    public LocalTime getStationArrivalTime(int index) {
        return routeList.get(index).arrivalTime;
    }

    public int getStationStopDuration(int index) {
        return routeList.get(index).stopDuration;
    }

    public void addStation(String stationName, DayOfWeek arrivalDay, LocalTime arrivalTime, int stopDuration) {
        routeList.add(new Station(stationName, arrivalDay, arrivalTime, stopDuration));
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setStationArrivalDayTime(int index, DayOfWeek day, LocalTime time) {
        routeList.get(index).arrivalDay = day;
        routeList.get(index).arrivalTime = time;
    }

    public void setDepartureDayTime(DayOfWeek day, LocalTime time) {
        setStationArrivalDayTime(0, day, time);
    }

    public void setDefaultStopDuration(int minutes) {
        for (int i = 1; i < routeList.size(); i++) {
            routeList.get(i).stopDuration = minutes;
        }
    }

    public void setStationStopDuration(int stationOrder, int minutes) {
        routeList.get(stationOrder).stopDuration = minutes;
    }

    public void setStationStopDuration(String station, int minutes) {
        for (int i = 0; i < routeList.size(); i++) {
            if (routeList.get(i).stationName.equals(station)) {
                setStationStopDuration(i, minutes);
            }
        }
    }

    @Override
    public String toString() {
        return String.format("Мршрут: %s\n %s", title, routeList);
    }
}
