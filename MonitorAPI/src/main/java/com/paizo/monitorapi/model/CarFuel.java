package com.paizo.monitorapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "carFuel")
@Data
@ToString(callSuper=true)
public class CarFuel extends IOTDevice {

    @Id
    private String id;
    private Boolean isEmpty;
    private Integer capacity;
    private Integer fuelLevel;

    @Builder
    public CarFuel(String deviceId, String vendor, LocalDateTime time, String id, Boolean isEmpty, Integer capacity, Integer fuelLevel) {
        super(deviceId, vendor, time);
        this.id = id;
        this.isEmpty = isEmpty;
        this.capacity = capacity;
        this.fuelLevel = fuelLevel;
    }
}
