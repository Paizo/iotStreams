package com.github.paizo.monitorapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SmartCouch {

    @Indexed
    private String deviceId;
    private String vendor;
    private Date time;
    private Boolean bluetoothEnabled;
    private Boolean bluetoothPaired;
    private Integer temperature;
    private Integer pressure;

}
