package com.epam.lab.rto.dto;

import java.util.ArrayList;
import java.util.List;

public class Route {

    private String title;
    private List<Station> stationList = new ArrayList<>();
    private Locomotive locomotive;

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

    public Route(String title, Locomotive locomotive) {
        this.title = title;
        this.locomotive = locomotive;
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
        if (index < 0 || index >= stationList.size()) {
            throw new NullPointerException();
        }
        return stationList.get(index).travelTime;
    }

    public int getStationTravelTime(String name) {
        return getStationTravelTime(getStationIndex(name));
    }

    public int getStationStopDuration(int index) {
        if (index < 0 || index >= stationList.size()) {
            throw new NullPointerException();
        }
        return stationList.get(index).stopDuration;
    }

    public int getStationStopDuration(String name) {
        return getStationStopDuration(getStationIndex(name));
    }

    public List<Station> getStations() {
        return this.stationList;
    }

    public void setAllTravelTime(int minutes) {
        for (Station station : stationList) {
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

    public Locomotive getLocomotive() {
        return locomotive;
    }

    public void setLocomotive(Locomotive locomotive) {
        this.locomotive = locomotive;
    }

    public List<Station> getStationList() {
        return stationList;
    }

    private int getStationIndex(String name) {
        for (int i = 0; i < stationList.size(); i++) {
            if (stationList.get(i).getStationName().equals(name)) {
                return i;
            }
        }
        return -1;
    }
}
