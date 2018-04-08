package com.paizo.monitorapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "CarFuel")
@Data
@ToString(callSuper=true)
public class CarFuel extends IOTDevice {

    @Id
    private String id;
    private List<DecimalSensor> sensorsReadings;
    private Boolean isEmpty;
    private BigDecimal capacity;

    @Builder
    public CarFuel(String deviceId, String vendor, String macAddress, LocalDateTime time, String id, List<DecimalSensor> sensorsReadings, Boolean isEmpty, BigDecimal capacity) {
        super(deviceId, vendor, macAddress, time);
        this.id = id;
        this.sensorsReadings = sensorsReadings;
        this.isEmpty = isEmpty;
        this.capacity = capacity;
    }
}
