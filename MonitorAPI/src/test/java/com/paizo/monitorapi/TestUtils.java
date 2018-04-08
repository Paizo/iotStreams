package com.paizo.monitorapi;

import com.paizo.monitorapi.model.*;
import com.paizo.monitorapi.service.UtilityService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class TestUtils {

    @Autowired
    private UtilityService utilityService;

    public Refrigerator generateRefrigeratorReadings() {
        Refrigerator refrigerator = Refrigerator
                .builder()
                .deviceId(RandomStringUtils.randomAlphabetic(3,10))
                .macAddress(RandomStringUtils.randomAlphabetic(3,10))
                .time(LocalDateTime.now())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .sensorsReadings(Arrays.asList
                    (
                        generateTemperatureSensorData(),
                        generateTemperatureSensorData(),
                        generateTemperatureSensorData(),
                        generateTemperatureSensorData(),
                        generateTemperatureSensorData()
                    )
                )
                .build();
        return refrigerator;
    }

    public SmartCouch generateSmartCouchReadings() {
        SmartCouch smartCouch = SmartCouch
                .builder()
                .deviceId(RandomStringUtils.randomAlphabetic(3,10))
                .macAddress(RandomStringUtils.randomAlphabetic(3,10))
                .time(LocalDateTime.now())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .sensorsReadings(Arrays.asList
                    (
                        generateTemperatureSensorData(),
                        generateTemperatureSensorData(),
                        generateTemperatureSensorData(),
                        generateTemperatureSensorData(),
                        generatePressureSensorData()
                    )
                )
                .bluetoothEnabled(RandomUtils.nextBoolean())
                .bluetoothPaired(RandomUtils.nextBoolean())
                .build();
        return smartCouch;
    }

    public CarFuel generateCarFuelReadings() {
        CarFuel carFuel = CarFuel
                .builder()
                .deviceId(RandomStringUtils.randomAlphabetic(3,10))
                .macAddress(RandomStringUtils.randomAlphabetic(3,10))
                .time(LocalDateTime.now())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .sensorsReadings(Arrays.asList
                        (
                            generateFuelLevelSensorData(),
                            generateFuelLevelSensorData(),
                            generateFuelLevelSensorData(),
                            generateFuelLevelSensorData(),
                            generateFuelLevelSensorData()
                        )
                )
                .capacity(
                        utilityService.createBigDecimalInstance(
                            RandomUtils.nextDouble(0, 99)
                        )
                )
                .isEmpty(RandomUtils.nextBoolean())
                .build();
        return carFuel;
    }

    public SmartWatch generateSmartWatchReadings() {
        SmartWatch smartWatch = SmartWatch
                .builder()
                .deviceId(RandomStringUtils.randomAlphabetic(3,10))
                .macAddress(RandomStringUtils.randomAlphabetic(3,10))
                .time(LocalDateTime.now())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .sensorsReadings(Arrays.asList
                        (
                            generateHearthBeatSensorData(),
                            generateHearthBeatSensorData(),
                            generateHearthBeatSensorData(),
                            generateHearthBeatSensorData(),
                            generateHearthBeatSensorData()
                        )
                )
                .inUse(RandomUtils.nextBoolean())
                .build();
        return smartWatch;
    }

    private DecimalSensor generateHearthBeatSensorData() {
        return DecimalSensor.
                builder()
                .sensorType(SensorType.HEART_BEAT)
                .name(RandomStringUtils.randomAlphabetic(3,10))
                .value(
                        utilityService.createBigDecimalInstance(
                                RandomUtils.nextDouble(0, 999)
                        )
                )
                .time(LocalDateTime.now())
                .build();
    }

    private DecimalSensor generatePressureSensorData() {
        return DecimalSensor.
                builder()
                .sensorType(SensorType.PRESSURE)
                .name(RandomStringUtils.randomAlphabetic(3,10))
                .value(
                        utilityService.createBigDecimalInstance(
                                RandomUtils.nextDouble(0, 99)
                        )
                )
                .time(LocalDateTime.now())
                .build();
    }

    private DecimalSensor generateTemperatureSensorData() {
        return DecimalSensor.
                builder()
                .sensorType(SensorType.TEMPERATURE)
                .name(RandomStringUtils.randomAlphabetic(3,10))
                .value(
                        utilityService.createBigDecimalInstance(
                                RandomUtils.nextDouble(0, 99)
                        )
                )
                .time(LocalDateTime.now())
                .build();
    }

    private DecimalSensor generateFuelLevelSensorData() {
        return DecimalSensor.
                builder()
                .sensorType(SensorType.FUEL_LEVEL)
                .name(RandomStringUtils.randomAlphabetic(3,10))
                .value(
                        utilityService.createBigDecimalInstance(
                                RandomUtils.nextDouble(0, 99)
                        )
                )
                .time(LocalDateTime.now())
                .build();
    }
}
