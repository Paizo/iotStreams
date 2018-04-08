package com.paizo.monitorapi.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.geo.Point;

import java.time.LocalDateTime;

@Data
public class LocationSensor extends Sensor {

    private Point value;

    @Builder
    public LocationSensor(String name, SensorType sensorType, LocalDateTime time, Point value) {
        super(name, sensorType, time);
        this.value = value;
    }
}
