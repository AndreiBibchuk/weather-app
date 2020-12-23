package com.andrei.weather.controller;

import com.andrei.weather.dto.StatisticInfoDTO;
import com.andrei.weather.service.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/statistics")
@RequiredArgsConstructor
public class StatisticController {

    private final StatisticService service;

    @GetMapping("history")
    public StatisticInfoDTO getBalloon(@RequestParam String dateFrom,
                                       @RequestParam String dateTo) {

        return service.getStatisticInDateRange(dateFrom, dateTo);
    }

}
