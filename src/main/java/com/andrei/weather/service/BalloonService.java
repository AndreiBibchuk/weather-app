package com.andrei.weather.service;

import com.andrei.weather.converter.BalloonDataConverter;
import com.andrei.weather.model.BalloonMessage;
import com.andrei.weather.repository.BalloonMessageRepository;
import com.andrei.weather.util.BalloonDataParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalloonService {

    private final BalloonMessageRepository repo;
    private final StatisticService statisticService;
    private final BalloonDataConverter converter;

    @Transactional
    public void processBalloonMessage(String message) {
        BalloonMessage balloonMessage = BalloonDataParser.parseBalloonMessage(message);
        BalloonMessage defaultFormattedMessage = converter.toDefaultUnits(balloonMessage);
        repo.save(defaultFormattedMessage);
        statisticService.updateStatisticInfo(defaultFormattedMessage);
    }
}
