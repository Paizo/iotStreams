package com.github.paizo.producer.model;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Refrigerator {

    private String deviceId;
    private String vendor;
    private Date time;
    private Integer temperature;
    private Integer spoiledItems;

}
