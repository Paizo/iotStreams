package com.github.paizo.monitorapi.converter;

import org.springframework.core.convert.converter.Converter;

import java.math.BigDecimal;

//@Component
public class BigDecimalToDoubleConverter implements Converter<BigDecimal, Double> {

    @Override
    public Double convert(BigDecimal source) {
        return source.doubleValue();
    }
}