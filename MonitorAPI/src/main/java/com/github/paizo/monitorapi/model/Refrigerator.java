package com.github.paizo.monitorapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.util.Date;

/**
 * The default collection name that is used is the class name changed to start with a lower-case letter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Refrigerator {

    @Indexed
    private String deviceId;
    private String vendor;
    private Date time;
    private String sensorId;
    private String sensorType;
    private Integer sensorValue;

}
