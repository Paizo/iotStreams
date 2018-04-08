package com.paizo.monitorapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "refrigerator")
@Data
@ToString(callSuper=true)
public class Refrigerator extends IOTDevice {

    @Id
    private String id;
    private List<DecimalSensor> sensorsReadings;

    @Builder
    public Refrigerator(String deviceId, String vendor, String macAddress, LocalDateTime time, List<DecimalSensor> sensorsReadings) {
        super(deviceId, vendor, macAddress, time);
        this.sensorsReadings = sensorsReadings;
    }
}
