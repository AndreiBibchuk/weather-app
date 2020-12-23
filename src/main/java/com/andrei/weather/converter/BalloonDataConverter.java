package com.andrei.weather.converter;

import com.andrei.weather.model.BalloonMessage;
import com.andrei.weather.model.enums.Observatory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class BalloonDataConverter {

    public static final String COORDINATE_SEPARATOR = ",";
    public static final String MESSAGE_SEPARATOR = "|";

    public BalloonMessage toDefaultUnits(BalloonMessage message) {

        Observatory observatory = message.getObservatory();

        switch (observatory) {
            case AU:
                return new BalloonMessage(
                        message.getTimestamp(),
                        message.getLocation(),
                        celsiusToKelvin(message.getTemperature()),
                        message.getObservatory());

            case US:
                return new BalloonMessage(
                        message.getTimestamp(),
                        milesToKm(message.getLocation()),
                        fahrenheitToKelvin(message.getTemperature()),
                        message.getObservatory());

            case FR:
                return new BalloonMessage(
                        message.getTimestamp(),
                        metresToKm(message.getLocation()),
                        message.getTemperature(),
                        message.getObservatory());

            default:
                return message;
        }
    }

    private Double celsiusToKelvin(Double temperature){
        return temperature + 273;
    }

    private Double fahrenheitToKelvin(Double temperature){
        return (temperature + 459.67)*((double)5 /9);
    }

    private String metresToKm(String location){
        String[] distances = location.split(COORDINATE_SEPARATOR);
        int distanceX = Math.round(Float.valueOf(distances[0]) / 1000);
        int distanceY = Math.round(Float.valueOf(distances[1]) / 1000);

        return new StringBuilder().append(distanceX).append(COORDINATE_SEPARATOR).append(distanceY).toString();
    }

    private String milesToKm(String location){
        String[] distances = location.split(COORDINATE_SEPARATOR);

        long distanceX = Math.round(Integer.valueOf(distances[0])*1.609);
        long distanceY = Math.round(Float.valueOf(distances[1])*1.609);

        return new StringBuilder().append(distanceX).append(COORDINATE_SEPARATOR).append(distanceY).toString();
    }
}
