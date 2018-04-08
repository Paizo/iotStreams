package com.paizo.monitorapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "CarFuel")
@Data
@ToString(callSuper=true)
public class SmartWatch extends IOTDevice {

    @Id
    private String id;
    private List<DecimalSensor> sensorsReadings;
    private Boolean inUse;

    @Builder
    public SmartWatch(String deviceId, String vendor, String macAddress, LocalDateTime time, String id, List<DecimalSensor> sensorsReadings, Boolean inUse) {
        super(deviceId, vendor, macAddress, time);
        this.id = id;
        this.sensorsReadings = sensorsReadings;
        this.inUse = inUse;
    }
}
