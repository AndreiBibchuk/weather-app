package com.andrei.weather.controller;

import com.andrei.weather.service.ProducerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/produce")
@RequiredArgsConstructor
public class MessageProducerController {

    private final ProducerService service;

    @PostMapping
    public void processBalloonMessage(@RequestParam String message) {
        service.produce(message);
    }

    @GetMapping
    public void generateTestData() {
        service.generateTestData();
    }
}
