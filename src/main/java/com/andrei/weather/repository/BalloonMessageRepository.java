package com.andrei.weather.repository;

import com.andrei.weather.model.BalloonMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BalloonMessageRepository extends JpaRepository<BalloonMessage, Long> {

    List<BalloonMessage> findWeatherByTimestampBetween(LocalDateTime dateFrom, LocalDateTime dateTo);

    BalloonMessage findFirstByTimestamp(LocalDateTime date);
}
