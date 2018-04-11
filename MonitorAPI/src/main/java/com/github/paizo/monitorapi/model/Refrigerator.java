package com.github.paizo.monitorapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refrigerator {

    private String deviceId;
    private String vendor;
    private LocalDateTime time;
    private Integer temperature;
    private Integer spoiledItems;

}
