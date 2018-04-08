package com.paizo.monitorapi.model;

import lombok.AllArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@AllArgsConstructor
@ToString
public class IOTDevice {

    public String deviceId;
    public String vendor;
    public String macAddress;
    protected LocalDateTime time;
}
