package com.epam.lab.rto.repository.interfaces;

import com.epam.lab.rto.dto.GraphMap;

import java.util.List;

public interface IStationRepository {

    List<String> getAllStations();

    GraphMap getStationMap();

}