package com.github.paizo.monitorapi.model.projection;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AverageTemperature {

    private String deviceId;
    private BigDecimal averageTemperature;

}
