package com.epam.lab.rto.service;

import com.epam.lab.rto.dto.Carriage;
import com.epam.lab.rto.dto.Locomotive;
import com.epam.lab.rto.repository.interfaces.ICarriageRepository;
import com.epam.lab.rto.repository.interfaces.ILocomotiveRepository;
import com.epam.lab.rto.repository.interfaces.ITrainCompositionRepository;
import com.epam.lab.rto.service.interfaces.ITrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService implements ITrainService {

    @Autowired
    private ITrainCompositionRepository trainCompositionRepository;

    @Autowired
    private ILocomotiveRepository locomotiveRepository;

    @Autowired
    private ICarriageRepository carriageRepository;


    @Override
    public List<Locomotive> getAllLocomotives() {
        return locomotiveRepository.getAllLocomotives();
    }

    @Override
    public Locomotive getLocomotiveByName(String locomotiveName) {
        return locomotiveRepository.getLocomotiveByName(locomotiveName);
    }

    @Override
    public List<Carriage> getAllCarriages() {
        return carriageRepository.getAllCarriages();
    }

    @Override
    public Carriage getCarriageByName(String carriageName) {
        return carriageRepository.getCarriageByName(carriageName);
    }

    @Override
    public int increaseSoldPlaceByTripIdAndCarriageId(long tripId, long carriageId) {
        return trainCompositionRepository.increaseSoldPlaceByTripIdAndCarriageId(tripId, carriageId);
    }

    @Override
    public int decreaseSoldPlaceByTripIdAndCarriageId(long tripId, long carriageId) {
        return trainCompositionRepository.decreaseSoldPlaceByTripIdAndCarriageId(tripId, carriageId);
    }
}