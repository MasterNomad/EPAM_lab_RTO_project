package com.epam.lab.rto.dto;

public class Locomotive {
    private long id;
    private String name;
    private int average_speed;

    public Locomotive(long id, String name, int average_speed) {
        this.id = id;
        this.name = name;
        this.average_speed = average_speed;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAverage_speed(int average_speed) {
        this.average_speed = average_speed;
    }

    public Locomotive() {
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAverage_speed() {
        return average_speed;
    }
}
