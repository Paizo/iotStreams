package com.paizo.monitorapi.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Sensor {

    private String name;
    private SensorType sensorType;
    private LocalDateTime time;
}
