package com.github.paizo.monitorapi.service;

import com.github.paizo.monitorapi.model.rest.SingleSensorReading;

import java.util.Date;
import java.util.List;

public interface IOTDeviceService {

    SingleSensorReading averageSensorValueByDeviceIdSensorIdAndDateRange(String deviceId, String sensorId, Date from, Date to);

    SingleSensorReading minSensorValueByDeviceIdSensorIdAndDateRange(String deviceId, String sensorId, Date from, Date to);

    SingleSensorReading maxSensorValueByDeviceIdSensorIdAndDateRange(String deviceId, String sensorId, Date from, Date to);

    SingleSensorReading medianSensorValueByDeviceIdSensorIdAndDateRange(String deviceId, String sensorId, Date from, Date to);

    List<SingleSensorReading> averageReadings4DeviceIdSensorIdsAndDateRange(String deviceId, Date from, Date to, String... sensorsId);

    List<SingleSensorReading> maxReadings4DeviceIdSensorIdsAndDateRange(String deviceId, Date from, Date to, String... sensorsId);

    List<SingleSensorReading> minReadings4DeviceIdSensorIdsAndDateRange(String deviceId, Date from, Date to, String... sensorsId);

    List<SingleSensorReading> medianReadings4DeviceIdSensorIdsAndDateRange(String deviceId, Date from, Date to, String... sensorsId);
}
