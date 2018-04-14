package com.github.paizo.producer.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class CarFuel {

    private String deviceId;
    private String vendor;
    private Date time;
    private Boolean isEmpty;
    private Integer capacity;
    private Integer fuelLevel;

}
