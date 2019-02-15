package com.epam.lab.rto.services;

import com.epam.lab.rto.dto.GraphMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StationMapService {

    @Autowired
    private GraphMap stationMap;

    public int getDistance (String firstStation, String secondStation) {
        List<String> way = stationMap.getWay(firstStation, secondStation);
        return stationMap.getStringWayDistance(way);
    }

    public List<String> createRouteWay(String... args){
        if (args.length < 2) {
            return null;
        }
        return stationMap.getWay(args);
    }

}
