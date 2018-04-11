package com.github.paizo.kafka2mongo.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
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
