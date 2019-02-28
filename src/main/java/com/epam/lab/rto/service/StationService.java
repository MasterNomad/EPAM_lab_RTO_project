package com.epam.lab.rto.service;

import com.epam.lab.rto.repository.StationRepository;
import com.epam.lab.rto.dto.GraphMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class StationService {

    @Autowired
    private StationRepository stationRepository;

    @Autowired
    private WayService wayService;

    public List<String> getAllStations() {
        return stationRepository.getAllStations();
    }


    public List<String> createRouteWay(String... args) {
        if (args.length < 2) {
            return null;
        }
        GraphMap stationMap = stationRepository.getStationMap();
        args = Arrays.stream(args)
                .filter(s -> (s != null && s.length() > 0))
                .toArray(String[]::new);
        return wayService.getWay(stationMap, args);
    }

    public int getDistance(String startStation, String finishStation) {
        GraphMap stationMap = stationRepository.getStationMap();
        List<String> way = wayService.getWay(stationMap, startStation, finishStation);
        return wayService.getWayDistance(stationMap, way);
    }

}
