package com.andrei.weather.service;

import com.andrei.weather.dto.StatisticInfo;
import com.andrei.weather.dto.StatisticInfoDTO;
import com.andrei.weather.model.BalloonMessage;
import com.andrei.weather.model.enums.Observatory;
import com.andrei.weather.repository.BalloonMessageRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.andrei.weather.converter.BalloonDataConverter.COORDINATE_SEPARATOR;
import static com.andrei.weather.util.BalloonDataParser.DATE_TIME_FORMATTER;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticService {

    private final BalloonMessageRepository repo;

    public void updateStatisticInfo(BalloonMessage message) {

        StatisticInfo statistics = StatisticInfo.getInstance();
        if(statistics == null || CollectionUtils.isEmpty(statistics.getObservationsCount())){
            initStatistic(statistics, message);
        } else {
            updateStatistic(statistics, message);
        }
    }

    public StatisticInfoDTO getStatisticInDateRange (String dateFrom, String dateto){
        LocalDateTime from = LocalDateTime.parse(dateFrom, DATE_TIME_FORMATTER);
        LocalDateTime to = LocalDateTime.parse(dateto, DATE_TIME_FORMATTER);
        List<BalloonMessage> messages = repo.findWeatherByTimestampBetween(from, to);

        if(CollectionUtils.isEmpty(messages)){
            return new StatisticInfoDTO();
        } else
            return generateStatisticByMessageList(messages);
    }

    private void initStatistic(StatisticInfo statistics, BalloonMessage message) {
        String[] coordinates = message.getLocation().split(COORDINATE_SEPARATOR);
        statistics.setMinTemperature(message.getTemperature());
        statistics.setMaxTemperature(message.getTemperature());
        statistics.setMeanTemperature(message.getTemperature());
        statistics.getObservationsCount().put(message.getObservatory(), 1);
        statistics.setDistance(0.0);
        statistics.setPreviousX(Double.valueOf(coordinates[0]));
        statistics.setPreviousY(Double.valueOf(coordinates[1]));
    }

    private void updateStatistic(StatisticInfo statistics, BalloonMessage message) {
        String[] coordinates = message.getLocation().split(COORDINATE_SEPARATOR);
        statistics.setMinTemperature(checkMinTemperature(message.getTemperature()));
        statistics.setMaxTemperature(checkMaxTemperature(message.getTemperature()));
        statistics.setMeanTemperature(checkMeanTemperature(message.getTemperature()));
        updateObservationsCount(message);
        statistics.setDistance(recalculateCurrentDistance(message));
        statistics.setPreviousX(Double.valueOf(coordinates[0]));
        statistics.setPreviousY(Double.valueOf(coordinates[1]));
    }

    private Double checkMinTemperature (Double temperature){
        Double minTemperature = StatisticInfo.getInstance().getMinTemperature();

        return temperature < minTemperature ? temperature : minTemperature;
    }

    private Double checkMaxTemperature (Double temperature){
        Double maxTemperature = StatisticInfo.getInstance().getMaxTemperature();

        return temperature > maxTemperature ? temperature : maxTemperature;
    }

    private Double checkMeanTemperature (Double temperature){

        Double oldMeanTemperature = StatisticInfo.getInstance().getMeanTemperature();
        Integer observationsCount = StatisticInfo.getInstance().getObservationsCount().values().stream()
                .reduce(0, Integer::sum);

        return ((oldMeanTemperature*observationsCount + temperature)/(observationsCount + 1));
    }

    private void updateObservationsCount(BalloonMessage message){

        Map<Observatory, Integer> observationsCountMap = StatisticInfo.getInstance().getObservationsCount();
        Observatory observatory = message.getObservatory();
        if(observationsCountMap.get(observatory) == null){
            observationsCountMap.put(observatory, 1);
        } else {
            Integer count = observationsCountMap.get(observatory);
            observationsCountMap.replace(observatory, count+1);
        }
    }

    private Double recalculateCurrentDistance (BalloonMessage message){
        String[] coordinates = message.getLocation().split(COORDINATE_SEPARATOR);
        Double currentX = Double.valueOf(coordinates[0]);
        Double currentY = Double.valueOf(coordinates[1]);
        Double previousX = StatisticInfo.getInstance().getPreviousX();
        Double previousY = StatisticInfo.getInstance().getPreviousY();

        Double distance = Math.sqrt(Math.pow(Math.abs(previousX - currentX), 2) + Math.pow(Math.abs(previousY - currentY), 2));

        return (StatisticInfo.getInstance().getDistance() + distance);
    }

    private StatisticInfoDTO generateStatisticByMessageList (List<BalloonMessage> messages){

        List<Double> temperatures = messages.stream().map(BalloonMessage::getTemperature).collect(Collectors.toList());
        Double minTemperature = Collections.min(temperatures);
        Double maxTemperature = Collections.max(temperatures);
        Double meanTemperature = temperatures.stream().mapToDouble(a -> a).average().orElse(0.0);
        Map<Observatory, Integer> observationsCount = new HashMap<>();
        for (BalloonMessage message : messages) {
            Observatory observatory = message.getObservatory();
            Integer count = observationsCount.get(observatory);
            if (count != null) {
                observationsCount.replace(observatory, count + 1);
            } else {
                observationsCount.put(observatory, 1);
            }
        }
        List<String> locations = messages.stream().map(BalloonMessage::getLocation).collect(Collectors.toList());

        Double totalDistance = 0.0;
        for (int i = 0; i < locations.size() - 1; i++) {
            Double distance = calculateDistance(locations.get(i),locations.get(i + 1));
            totalDistance += distance;
        }

        return new StatisticInfoDTO (
                minTemperature,
                maxTemperature,
                meanTemperature,
                observationsCount,
                totalDistance);
    }

    private Double calculateDistance(String firstLocation, String secondLocation){
        String[] firstCoordinates = firstLocation.split(COORDINATE_SEPARATOR);
        Double firstX = Double.valueOf(firstCoordinates[0]);
        Double firstY = Double.valueOf(firstCoordinates[1]);
        String[] secondCoordinates = secondLocation.split(COORDINATE_SEPARATOR);
        Double secondX = Double.valueOf(secondCoordinates[0]);
        Double secondY = Double.valueOf(secondCoordinates[1]);

        return Math.sqrt(Math.pow(Math.abs(firstX - secondX), 2) + Math.pow(Math.abs(firstY - secondY), 2));
    }



}
