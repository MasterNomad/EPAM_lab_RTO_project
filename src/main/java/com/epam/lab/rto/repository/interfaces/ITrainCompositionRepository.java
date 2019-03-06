package com.epam.lab.rto.repository.interfaces;

import com.epam.lab.rto.dto.CarriageComposition;

import java.util.List;

public interface ITrainCompositionRepository {

    CarriageComposition addTrainComposition(CarriageComposition carriageComposition);

    List<CarriageComposition> addTrainCompositionList(List<CarriageComposition> carriageCompositions);

    List<CarriageComposition> getTrainCompositionByTripId(long tripId);

    int increaseSoldPlaceByTripIdAndCarriageId(long tripId, long carriageId);

    int decreaseSoldPlaceByTripIdAndCarriageId(long tripId, long carriageId);
}