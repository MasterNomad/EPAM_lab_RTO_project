package com.epam.lab.rto.repository;

import com.epam.lab.rto.dto.GraphMap;
import com.epam.lab.rto.repository.interfaces.IStationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import java.util.*;

@Repository
public class StationRepository implements IStationRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public List<String> getAllStations() {
        String sql = "SELECT name " +
                "FROM stations " +
                "ORDER BY id";

        return jdbcTemplate.queryForList(sql, String.class);
    }

    @Override
    public GraphMap getStationMap() {
        GraphMap stationMap = new GraphMap();
        String sql = "SELECT * " +
                "FROM station_connections";
        SqlRowSet result = jdbcTemplate.queryForRowSet(sql);
        while (result.next()){
            String firstStation = result.getString("station_1").trim();
            String secondStation = result.getString("station_2").trim();
            int distance = result.getInt("distance");
            if (!stationMap.contains(firstStation)){
                stationMap.add(firstStation);
            }
            if (!stationMap.contains(secondStation)){
                stationMap.add(secondStation);
            }
            stationMap.addConnection(firstStation, secondStation, distance);
            stationMap.addConnection(secondStation, firstStation, distance);
        }
        return stationMap;
    }
}