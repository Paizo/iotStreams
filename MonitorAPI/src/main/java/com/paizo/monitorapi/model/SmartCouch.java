package com.paizo.monitorapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "smartCouch")
@Data
@ToString(callSuper=true)
public class SmartCouch extends IOTDevice {

    @Id
    private String id;
    private Boolean bluetoothEnabled;
    private Boolean bluetoothPaired;
    private Integer temperature;
    private Integer pressure;

    @Builder
    public SmartCouch(String deviceId, String vendor, LocalDateTime time, String id, Boolean bluetoothEnabled, Boolean bluetoothPaired, Integer temperature, Integer pressure) {
        super(deviceId, vendor, time);
        this.id = id;
        this.bluetoothEnabled = bluetoothEnabled;
        this.bluetoothPaired = bluetoothPaired;
        this.temperature = temperature;
        this.pressure = pressure;
    }
}
