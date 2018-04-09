package com.paizo.monitorapi.model;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "smartWatch")
@Data
@ToString(callSuper=true)
public class SmartWatch extends IOTDevice {

    @Id
    private String id;
    private Integer heartBeat;
    private Boolean inUse;

    @Builder
    public SmartWatch(String deviceId, String vendor, LocalDateTime time, String id, Integer heartBeat, Boolean inUse) {
        super(deviceId, vendor, time);
        this.id = id;
        this.heartBeat = heartBeat;
        this.inUse = inUse;
    }
}
