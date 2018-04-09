package com.paizo.monitorapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "refrigerator")
@Data
@ToString(callSuper=true)
public class Refrigerator extends IOTDevice {

    @Id
    private String id;
    private Integer temperature;
    private Integer spoiledItems;

    @Builder
    public Refrigerator(String deviceId, String vendor, LocalDateTime time, String id, Integer temperature, Integer spoiledItems) {
        super(deviceId, vendor, time);
        this.id = id;
        this.temperature = temperature;
        this.spoiledItems = spoiledItems;
    }
}
