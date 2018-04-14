package com.github.paizo.kafka2mongo.model;

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
public class Refrigerator {

    @Indexed
    private String deviceId;
    private String vendor;
    private Date time;
    private Integer temperature;
    private Integer spoiledItems;

}
