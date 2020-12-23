package com.andrei.weather.util;

import com.andrei.weather.model.BalloonMessage;
import com.andrei.weather.exception.MessageFormatException;
import com.andrei.weather.model.enums.Observatory;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@UtilityClass
public class BalloonDataParser {

    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final String MESSAGE_FORMAT = "timestamp|location|temperature|observatory";

    public static BalloonMessage parseBalloonMessage(String inputMessage) {

        String[] parts = splitMessage(inputMessage);
        LocalDateTime timestamp = LocalDateTime.parse(parts[0], DATE_TIME_FORMATTER);
        String location = parts[1];
        Double temperature = Double.parseDouble(parts[2]);
        Observatory observatory = Observatory.valueOf(parts[3]);

        return new BalloonMessage(timestamp, location, temperature, observatory);
    }

    private static String[] splitMessage(String message) {
        if (StringUtils.isBlank(message)) {
            throw new MessageFormatException("Empty message received");
        }
        String[] parts = message.split("\\|");
        if (parts.length != 4) {
            throw new MessageFormatException("Wrong message format. Expected: " + MESSAGE_FORMAT);
        } else {
            return parts;
        }
    }
}
