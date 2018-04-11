package com.github.paizo.monitorapi.service;

import com.github.paizo.monitorapi.model.projection.AverageTemperature;

public interface RefrigeratorService {

    AverageTemperature averageTemperature();

    AverageTemperature averageTemperatureByDeviceId(String deviceId);

}
