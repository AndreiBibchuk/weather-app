package com.andrei.weather.dto;

import com.andrei.weather.model.enums.Observatory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatisticInfoDTO {

    private Double minTemperature;
    private Double maxTemperature;
    private Double meanTemperature;
    private Map<Observatory, Integer> observationsCount = new HashMap<>();
    private Double distance;
}
