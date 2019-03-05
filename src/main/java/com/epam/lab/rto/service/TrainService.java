package com.epam.lab.rto.service;

import com.epam.lab.rto.dto.Locomotive;
import com.epam.lab.rto.repository.LocomotiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrainService {

    @Autowired
    private LocomotiveRepository locomotiveRepository;

    public Locomotive getLocomotiveByName (String locomotiveName) {
        return locomotiveRepository.getLocomotiveByName(locomotiveName);
    }
}
