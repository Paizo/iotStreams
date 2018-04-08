package com.paizo.monitorapi.service.impl;

import com.paizo.monitorapi.config.BigDecimalConfiguration;
import com.paizo.monitorapi.service.UtilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;

/**
 * Utility class to provide default values and functions to the application
 */
@Service
@Scope(value = "singleton")
public class UtilityServiceImpl implements UtilityService {

    @Autowired
    private BigDecimalConfiguration bigDecimalCfg;

    @PostConstruct
    public void postConstruct() {
        if (bigDecimalCfg == null) {
            throw new IllegalStateException("missing application configuration");
        }
    }

    @Override
    public BigDecimal createBigDecimalInstance(String value) {
        return new BigDecimal(value).setScale(bigDecimalCfg.getScale(), bigDecimalCfg.getRoundingMode());
    }

    @Override
    public BigDecimal createBigDecimalInstance(double value) {
        return new BigDecimal(value).setScale(bigDecimalCfg.getScale(), bigDecimalCfg.getRoundingMode());
    }
}
