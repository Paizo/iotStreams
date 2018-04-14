package com.github.paizo.producer.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class SmartWatch {

    private String deviceId;
    private String vendor;
    private Date time;
    private Integer heartBeat;
    private Boolean inUse;

}
