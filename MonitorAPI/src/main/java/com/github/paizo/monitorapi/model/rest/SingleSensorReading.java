package com.github.paizo.monitorapi.model.rest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SingleSensorReading {

    private String deviceId;
    private SensorReading sensorReading;
}
