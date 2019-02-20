package com.epam.lab.rto.dto;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private String title;
    private List<Station> stationList = new ArrayList<>();
    private int averageSpeed;

    public class Station {

        private String stationName;
        private int travelTime = 0;
        private int stopDuration = 0;


        private Station(String stationName, int travelTime, int stopDuration) {
            this.stationName = stationName;
            this.travelTime = travelTime;
            this.stopDuration = stopDuration;
        }

        private Station(String stationName) {
            this.stationName = stationName;
        }

        public String getStationName() {
            return stationName;
        }

        public int getStopDuration() {
            return stopDuration;
        }

        public int getTravelTime() {
            return travelTime;
        }

    }

    public Route(String title, int averageSpeed) {
        this.title = title;
        this.averageSpeed = averageSpeed;
        this.stationList = new ArrayList<>();
    }

    public Route(String title, List<String> stationList) {
        this.title = title;
        for (String stationName : stationList) {
            addStation(stationName);
        }
    }

    public void addStation(String stationName, int stopDuration, int travelTime) {
        stationList.add(new Station(stationName, travelTime, stopDuration));
    }

    public void addStation(String stationName) {
        addStation(stationName, 0, 0);
    }

    public int size() {
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

    public List<Station> getStations() {
        return this.stationList;
    }

//    public void setDefaultStopDuration(int minutes) {
//        for (int i = 1; i < stationList.size(); i++) {
//            stationList.get(i).stopDuration = minutes;
//        }
//    }

    public void setAllTravelTime(int minutes) {
        for (Station station: stationList) {
            station.travelTime = minutes;
        }
    }

    public void setStationStopDuration(int index, int minutes) {
        stationList.get(index).stopDuration = minutes;
    }

    public void setStationTravelTime(int index, int travelTime) {
        stationList.get(index).travelTime = travelTime;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
