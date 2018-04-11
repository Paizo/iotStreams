package com.github.paizo.producer.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class Refrigerator {

    private String deviceId;
    private String vendor;
    private LocalDateTime time;
    private Integer temperature;
    private Integer spoiledItems;

}
