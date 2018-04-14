package com.github.paizo.producer.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SmartCouch {

    private String deviceId;
    private String vendor;
    private Date time;
    private Boolean bluetoothEnabled;
    private Boolean bluetoothPaired;
    private Integer temperature;
    private Integer pressure;

}
