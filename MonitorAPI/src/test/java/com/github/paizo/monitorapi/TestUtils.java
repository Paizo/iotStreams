package com.github.paizo.monitorapi;

import com.github.paizo.monitorapi.model.CarFuel;
import com.github.paizo.monitorapi.model.Refrigerator;
import com.github.paizo.monitorapi.model.SmartCouch;
import com.github.paizo.monitorapi.model.SmartWatch;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TestUtils {

    public Refrigerator generateRefrigeratorReadings(String deviceId) {
        Refrigerator refrigerator = Refrigerator
                .builder()
                .deviceId(deviceId == null ? RandomStringUtils.randomAlphabetic(3,10) : deviceId)
                .time(LocalDateTime.now())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .spoiledItems(RandomUtils.nextInt(0,10))
                .temperature(RandomUtils.nextInt(-20, 100))
                .build();
        return refrigerator;
    }

    public SmartCouch generateSmartCouchReadings(String deviceId) {
        SmartCouch smartCouch = SmartCouch
                .builder()
                .deviceId(deviceId == null ? RandomStringUtils.randomAlphabetic(3,10) : deviceId)
                .time(LocalDateTime.now())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .bluetoothEnabled(RandomUtils.nextBoolean())
                .bluetoothPaired(RandomUtils.nextBoolean())
                .pressure(RandomUtils.nextInt(0,999))
                .temperature(RandomUtils.nextInt(-20, 100))
                .build();
        return smartCouch;
    }

    public CarFuel generateCarFuelReadings(String deviceId) {
        CarFuel carFuel = CarFuel
                .builder()
                .deviceId(deviceId == null ? RandomStringUtils.randomAlphabetic(3,10) : deviceId)
                .time(LocalDateTime.now())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .isEmpty(RandomUtils.nextBoolean())
                .capacity(RandomUtils.nextInt(1, 999))
                .fuelLevel(RandomUtils.nextInt(1, 999))
                .build();
        return carFuel;
    }

    public SmartWatch generateSmartWatchReadings(String deviceId) {
        SmartWatch smartWatch = SmartWatch
                .builder()
                .deviceId(deviceId == null ? RandomStringUtils.randomAlphabetic(3,10) : deviceId)
                .time(LocalDateTime.now())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .inUse(RandomUtils.nextBoolean())
                .heartBeat(RandomUtils.nextInt(0, 999))
                .build();
        return smartWatch;
    }

}
