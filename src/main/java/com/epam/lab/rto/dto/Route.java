package com.epam.lab.rto.dto;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class Route {

    private String title;
    private List<Station> stationList;
    private DayOfWeek departureDay;
    private LocalTime departureTime;
    private int averageSpeed;

    private class Station {

        private String stationName;
        private int stopDuration;
        private int travelTime;

        private Station(String stationName, int travelTime, int stopDuration) {
            this.stationName = stationName;
            this.stopDuration = stopDuration;
            this.travelTime = travelTime;
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

    public Route(String title, DayOfWeek departureDay, LocalTime departureTime, int averageSpeed) {
        this.title = title;
        this.departureDay = departureDay;
        this.departureTime = departureTime;
        this.averageSpeed = averageSpeed;
        this.stationList = new ArrayList<>();
    }

    public Route(String title) {
        this.title = title;
        this.stationList = new ArrayList<>();
    }

    public Route(String title, List<String> stationList, DayOfWeek departureDay, LocalTime departureTime, int defaultStopDuration, int averageSpeed) {
        this(title);
        for (String stationName : stationList) {
            this.stationList.add(new Station(stationName));
        }
        setAverageSpeed(averageSpeed);
        setDepartureDayTime(departureDay, departureTime);
        setDefaultStopDuration(defaultStopDuration);
    }

    public void addStation(String stationName, int stopDuration,  int travelTime) {
        stationList.add(new Station(stationName, stopDuration, travelTime));
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
        return stationList.size();
    }

    public String getStationName(int index) {
        return stationList.get(index).stationName;
    }

    public int getStationTravelTime(int index) {
        return stationList.get(index).travelTime;
    }

    public int getStationStopDuration(int index) {
        return stationList.get(index).stopDuration;
    }

    public int getStationTravelTime(int index, int travelTime) {
        return stationList.get(index).travelTime;
    }


    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public void setDepartureDayTime(DayOfWeek departureDay, LocalTime departureTime) {
        this.departureDay = departureDay;
        this.departureTime = departureTime;
    }

    public void setDefaultStopDuration(int minutes) {
        for (int i = 1; i < stationList.size(); i++) {
            stationList.get(i).stopDuration = minutes;
        }
    }

    public void setStationStopDuration(int index, int minutes) {
        stationList.get(index).stopDuration = minutes;
    }

    public void setStationTravelTime(int index, int travelTime) {
        stationList.get(index).travelTime = travelTime;
    }

    @Override
    public String toString() {
        return String.format("Мршрут: %s\n %s", title, stationList);
    }
}
