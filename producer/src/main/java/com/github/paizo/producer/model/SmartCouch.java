package com.github.paizo.producer.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class SmartCouch {

    private String deviceId;
    private String vendor;
    private LocalDateTime time;
    private Boolean bluetoothEnabled;
    private Boolean bluetoothPaired;
    private Integer temperature;
    private Integer pressure;

}
