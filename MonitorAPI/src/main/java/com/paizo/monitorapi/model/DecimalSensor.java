package com.paizo.monitorapi.model;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class DecimalSensor extends Sensor {

    private BigDecimal value;

    @Builder
    public DecimalSensor(String name, SensorType sensorType, LocalDateTime time, BigDecimal value) {
        super(name, sensorType, time);
        this.value = value;
    }
}
