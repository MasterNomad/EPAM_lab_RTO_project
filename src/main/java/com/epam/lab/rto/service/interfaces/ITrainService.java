package com.epam.lab.rto.service.interfaces;

import com.epam.lab.rto.dto.Carriage;
import com.epam.lab.rto.dto.Locomotive;

import java.util.List;

public interface ITrainService {

    List <Locomotive> getAllLocomotives();

    Locomotive getLocomotiveByName(String locomotiveName);

    List<Carriage> getAllCarriages();

    Carriage getCarriageByName(String carriageName);

    int increaseSoldPlaceByTripIdAndCarriageId(long tripId, long carriageId);

    int decreaseSoldPlaceByTripIdAndCarriageId(long tripId, long carriageId);

}