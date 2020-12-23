package com.andrei.weather.consumer;

import com.andrei.weather.dto.StatisticInfo;
import com.andrei.weather.service.BalloonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.andrei.weather.config.Config.DEFAULT_QUEUE;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeatherInfoConsumer {

    private final BalloonService balloonService;

    @RabbitListener(queues = DEFAULT_QUEUE)
    public void getMessage(String message) {
        try {
            log.info("New balloon message received. Message: {}", message);

            balloonService.processBalloonMessage(message);

            log.info("Weather statistics: {}", StatisticInfo.getInstance());
        } catch (Exception e) {
            log.error("Exception on processing message. " + e);
        }
    }
}
