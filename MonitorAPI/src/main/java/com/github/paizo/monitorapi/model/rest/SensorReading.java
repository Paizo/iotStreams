package com.github.paizo.monitorapi.model.rest;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class SensorReading {

    private String sensorId;
    private ReadingType readingType;
    private BigDecimal value;

}
