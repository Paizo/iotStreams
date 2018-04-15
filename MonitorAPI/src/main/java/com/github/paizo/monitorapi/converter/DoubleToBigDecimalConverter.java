package com.github.paizo.monitorapi.converter;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;
import java.math.RoundingMode;

//@Component
public class DoubleToBigDecimalConverter implements Converter<Double, BigDecimal> {

    @Override
    public BigDecimal convert(Double source) {
        return new BigDecimal(source).setScale(5, RoundingMode.HALF_EVEN);
    }
}