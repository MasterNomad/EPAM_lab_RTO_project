package com.epam.lab.rto.repository.interfaces;

import com.epam.lab.rto.dto.TrainComposition;

import java.util.List;

public interface ITrainCompositionRepository {

    TrainComposition addTrainComposition(TrainComposition trainComposition);

    List<TrainComposition> addTrainCompositionList(List<TrainComposition> trainCompositions);

    List<TrainComposition> getTrainCompositionByTripId(long tripId);

    int increaseSoldPlaceByTripIdAndCarriageId(long tripId, long carriageId);

    int decreaseSoldPlaceByTripIdAndCarriageId(long tripId, long carriageId);
}