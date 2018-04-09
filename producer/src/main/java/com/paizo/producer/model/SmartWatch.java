package com.paizo.producer.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SmartWatch {

    private String deviceId;
    private String vendor;
    private LocalDateTime time;
    private Integer heartBeat;
    private Boolean inUse;

}
