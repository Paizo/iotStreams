package com.paizo.monitorapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "smartCouch")
@Data
@ToString(callSuper=true)
public class SmartCouch extends IOTDevice {

    @Id
    private String id;
    private List<DecimalSensor> sensorsReadings;
    private Boolean bluetoothEnabled;
    private Boolean bluetoothPaired;

    @Builder
    public SmartCouch(String deviceId, String vendor, String macAddress, LocalDateTime time, String id, List<DecimalSensor> sensorsReadings, Boolean bluetoothEnabled, Boolean bluetoothPaired) {
        super(deviceId, vendor, macAddress, time);
        this.id = id;
        this.sensorsReadings = sensorsReadings;
        this.bluetoothEnabled = bluetoothEnabled;
        this.bluetoothPaired = bluetoothPaired;
    }

}
