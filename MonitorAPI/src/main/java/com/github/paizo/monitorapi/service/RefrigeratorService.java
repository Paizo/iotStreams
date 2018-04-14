package com.github.paizo.monitorapi.service;

import com.github.paizo.monitorapi.model.projection.AverageTemperature;
import com.github.paizo.monitorapi.model.projection.Median;

import java.util.Date;

public interface RefrigeratorService {

    AverageTemperature averageTemperature();

    AverageTemperature averageTemperatureByDeviceId(String deviceId);

    AverageTemperature averageTemperatureByDeviceIdAndDateRange(String deviceId, Date from, Date to);

    //see https://www.compose.com/articles/mongo-metrics-finding-a-happy-median/
    Median medianTemperature();
}
