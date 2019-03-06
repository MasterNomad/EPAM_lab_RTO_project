package com.epam.lab.rto.service.interfaces;

import com.epam.lab.rto.dto.GraphMap;

import java.util.List;

public interface IWayService {

    List<String> getWay(GraphMap graphMap, String... keys);

    int getWayDistance(GraphMap graphMap, List<String> way);
}