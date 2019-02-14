package com.epam.lab.rto.dao;


import com.epam.lab.rto.dto.GraphMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.*;

@Repository
public class StationMapRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private GraphMap stationMap = new GraphMap();

    public GraphMap getStationMap() {
        return stationMap;
    }

    @PostConstruct
    public void refreshStationMap() {
        stationMap.clear();

        String sql = "SELECT name FROM stations";
        for (Map<String, Object> result : jdbcTemplate.queryForList(sql)) {
            String name = result.get("name").toString();
            stationMap.add(name);
        }

        sql = "SELECT * FROM connections";
        for (Map<String, Object> result : jdbcTemplate.queryForList(sql)) {
            String firstStation = result.get("station_1").toString().trim();
            String secondStation = result.get("station_2").toString().trim();
            int distance = (int)result.get("distance");
            stationMap.addConnection(firstStation, secondStation, distance);
        }
    }

    public int getDistance (String firstStation, String secondStation) {
        List <String> way = stationMap.getWay(firstStation, secondStation);
        return stationMap.getStringWayDistance(way);
    }

}
