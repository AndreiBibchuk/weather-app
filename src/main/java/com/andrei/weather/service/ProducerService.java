package com.andrei.weather.service;

import com.andrei.weather.util.DataGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.andrei.weather.config.Config.DEFAULT_QUEUE;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProducerService {

    private final RabbitTemplate rabbitTemplate;

    public void produce(String message) {
        rabbitTemplate.convertAndSend(DEFAULT_QUEUE, message);
    }

    public void generateTestData(){
        List<String> testData = DataGenerator.generate();
        for (String data : testData) {
            produce(data);
        }
    }

}
