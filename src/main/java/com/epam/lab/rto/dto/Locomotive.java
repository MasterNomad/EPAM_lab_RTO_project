package com.epam.lab.rto.dto;

public class Locomotive {
    private long id;
    private String name;
    private int averageSpeed;

    public Locomotive(long id, String name, int averageSpeed) {
        this.id = id;
        this.name = name;
        this.averageSpeed = averageSpeed;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }
}
