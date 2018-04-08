package com.paizo.monitorapi.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.math.RoundingMode;

/**
 * Application configuration related to the BigDecimal fields;
 * define the scale and rounding to be used in sensor related operation
 */
@Configuration
@ConfigurationProperties(prefix="paizo.bigdecimal")
@Data
@ToString
public class BigDecimalConfiguration {
    private int scale;
    private RoundingMode roundingMode;
}
