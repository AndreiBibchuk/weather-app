package com.andrei.weather.dto;

import com.andrei.weather.model.enums.Observatory;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class StatisticInfo {

    private Double minTemperature;
    private Double maxTemperature;
    private Double meanTemperature;
    private Map<Observatory, Integer> observationsCount = new HashMap<>();
    private Double distance;

    private Double previousX;
    private Double previousY;

    private static class Loader {
        static final StatisticInfo INSTANCE = new StatisticInfo();
    }

    private StatisticInfo () {}

    public static StatisticInfo getInstance() {
        return Loader.INSTANCE;
    }

}
