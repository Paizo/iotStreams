package com.paizo.monitorapi.service;

import java.math.BigDecimal;

public interface UtilityService {

    BigDecimal createBigDecimalInstance(String value);

    BigDecimal createBigDecimalInstance(double value);

}
