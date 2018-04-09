package com.paizo.monitorapi.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class IOTDevice {

    private String deviceId;
    private String vendor;
    private LocalDateTime time;
}
