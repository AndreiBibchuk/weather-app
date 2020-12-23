package com.andrei.weather;

import com.andrei.weather.model.BalloonMessage;
import com.andrei.weather.model.enums.Observatory;
import com.andrei.weather.util.BalloonDataParser;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static com.andrei.weather.util.BalloonDataParser.DATE_TIME_FORMATTER;
import static org.assertj.core.api.Assertions.assertThat;

public class BalloonMessageTest {

    @Test
    public void messageParserTest() {
        String messageString = "2020-12-23T09:11|3,2|23|AU";
        BalloonMessage message = BalloonDataParser.parseBalloonMessage(messageString);
        assertThat(message.getTimestamp())
                .isEqualTo(LocalDateTime.parse("2020-12-23T09:11", DATE_TIME_FORMATTER));
        assertThat(message.getLocation()).isEqualTo("3,2");
        assertThat(message.getTemperature()).isEqualTo(23);
        assertThat(message.getObservatory()).isEqualTo(Observatory.AU);
    }
}
