package com.andrei.weather.model;

import com.andrei.weather.model.enums.Observatory;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "weather_data")
@NoArgsConstructor
@AllArgsConstructor
public class BalloonMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime timestamp;

    //In km by default
    private String location;

    //In kelvin by default
    private Double temperature;

    @Enumerated(EnumType.STRING)
    private Observatory observatory;

    public BalloonMessage(LocalDateTime timestamp, String location, Double temperature, Observatory observatory) {
        this.timestamp = timestamp;
        this.location = location;
        this.temperature = temperature;
        this.observatory = observatory;
    }
}
