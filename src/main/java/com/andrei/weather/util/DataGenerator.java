package com.andrei.weather.util;

import com.andrei.weather.model.enums.Observatory;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.andrei.weather.converter.BalloonDataConverter.COORDINATE_SEPARATOR;
import static com.andrei.weather.converter.BalloonDataConverter.MESSAGE_SEPARATOR;
import static com.andrei.weather.util.BalloonDataParser.DATE_TIME_FORMATTER;

@Slf4j
@UtilityClass
public class DataGenerator {

    public static List<String> generate() {
        List<String> testData = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            testData.add(new StringBuilder().append(LocalDateTime.now(ZoneOffset.UTC).format(DATE_TIME_FORMATTER))
                    .append(MESSAGE_SEPARATOR)
                    .append((new Random().nextInt(100)+ 1))
                    .append(COORDINATE_SEPARATOR)
                    .append((new Random().nextInt(100)+ 1))
                    .append(MESSAGE_SEPARATOR)
                    .append((new Random().nextInt(300)))
                    .append(MESSAGE_SEPARATOR)
                    .append(Observatory.values()[new Random().nextInt(Observatory.values().length)])
                    .toString());
        }

        return testData;
    }
}
