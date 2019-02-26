package com.epam.lab.rto.dto;

public class TripComposition {

    private long tripId;
    private Carriage carriage;
    private int amount;
    private int placesSold;

    public TripComposition() {
    }

    public TripComposition(Carriage carriage, int amount) {
        this.carriage = carriage;
        this.amount = amount;
    }

    public TripComposition(long tripId, Carriage carriage, int amount, int placesSold) {
        this.tripId = tripId;
        this.carriage = carriage;
        this.amount = amount;
        this.placesSold = placesSold;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public Carriage getCarriage() {
        return carriage;
    }

    public void setCarriage(Carriage carriage) {
        this.carriage = carriage;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getPlacesSold() {
        return placesSold;
    }

    public void setPlacesSold(int placesSold) {
        this.placesSold = placesSold;
    }
}
