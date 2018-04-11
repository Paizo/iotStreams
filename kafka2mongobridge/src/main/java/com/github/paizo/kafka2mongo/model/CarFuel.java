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
public class CarFuel {

    private String deviceId;
    private String vendor;
    private LocalDateTime time;
    private Boolean isEmpty;
    private Integer capacity;
    private Integer fuelLevel;

}
