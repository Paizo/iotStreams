package com.github.paizo.producer;

import com.github.paizo.producer.model.*;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class TestUtils {

    public Refrigerator generateRefrigeratorReadings(String deviceId) {
        Refrigerator refrigerator = Refrigerator
                .builder()
                .deviceId(deviceId == null ? RandomStringUtils.randomAlphabetic(3,10) : deviceId)
                .time(new Date())
                .sensorId(SensorId.values()[RandomUtils.nextInt(0, SensorId.values().length)].name())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .sensorValue(RandomUtils.nextInt(0, 100))
                .build();
        return refrigerator;
    }

    public SmartCouch generateSmartCouchReadings(String deviceId) {
        SmartCouch smartCouch = SmartCouch
                .builder()
                .deviceId(deviceId == null ? RandomStringUtils.randomAlphabetic(3,10) : deviceId)
                .time(new Date())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .sensorId(SensorId.values()[RandomUtils.nextInt(0, SensorId.values().length)].name())
                .sensorType(SensorType.values()[RandomUtils.nextInt(0, SensorType.values().length)].name())
                .sensorValue(RandomUtils.nextInt(0, 100))
                .build();
        return smartCouch;
    }

    public CarFuel generateCarFuelReadings(String deviceId) {
        CarFuel carFuel = CarFuel
                .builder()
                .deviceId(deviceId == null ? RandomStringUtils.randomAlphabetic(3,10) : deviceId)
                .time(new Date())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .sensorId(SensorId.values()[RandomUtils.nextInt(0, SensorId.values().length)].name())
                .sensorType(SensorType.values()[RandomUtils.nextInt(0, SensorType.values().length)].name())
                .sensorValue(RandomUtils.nextInt(0, 100))
                .build();
        return carFuel;
    }

    public SmartWatch generateSmartWatchReadings(String deviceId) {
        SmartWatch smartWatch = SmartWatch
                .builder()
                .deviceId(deviceId == null ? RandomStringUtils.randomAlphabetic(3,10) : deviceId)
                .time(new Date())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .sensorId(SensorId.values()[RandomUtils.nextInt(0, SensorId.values().length)].name())
                .sensorType(SensorType.values()[RandomUtils.nextInt(0, SensorType.values().length)].name())
                .sensorValue(RandomUtils.nextInt(0, 100))
                .build();
        return smartWatch;
    }

}
