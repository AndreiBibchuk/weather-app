package com.andrei.weather.model.enums;

import com.andrei.weather.model.enums.DistanceUnit;
import com.andrei.weather.model.enums.TemperatureUnit;
import lombok.Getter;

@Getter
public enum Observatory {

    AU(DistanceUnit.KM, TemperatureUnit.CELSIUS),
    US(DistanceUnit.MILES, TemperatureUnit.FAHRENHEIT),
    FR(DistanceUnit.M, TemperatureUnit.KELVIN),
    DEFAULT(DistanceUnit.KM, TemperatureUnit.KELVIN);

    private DistanceUnit distanceUnit;
    private TemperatureUnit temperatureUnit;

    Observatory(DistanceUnit distanceUnit, TemperatureUnit temperatureUnit) {
        this.temperatureUnit = temperatureUnit;
        this.distanceUnit = distanceUnit;
    }
}
