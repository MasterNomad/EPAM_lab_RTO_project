package com.epam.lab.rto.dto;

import lombok.Data;

@Data
public class CarriageComposition {

    private long tripId;
    private Carriage carriage;
    private int amount;
    private int placesSold;

    public CarriageComposition(Carriage carriage, int amount) {
        this.carriage = carriage;
        this.amount = amount;
    }

    public CarriageComposition(long tripId, Carriage carriage, int amount, int placesSold) {
        this.tripId = tripId;
        this.carriage = carriage;
        this.amount = amount;
        this.placesSold = placesSold;
    }

}
