package com.paizo.monitorapi;

import com.paizo.monitorapi.MonitorAPIApplication;
import com.paizo.monitorapi.TestUtils;
import com.paizo.monitorapi.model.DecimalSensor;
import com.paizo.monitorapi.model.Refrigerator;
import com.paizo.monitorapi.model.SensorType;
import com.paizo.monitorapi.repository.CarFuelRepository;
import com.paizo.monitorapi.repository.RefrigeratorRepository;
import com.paizo.monitorapi.repository.SmartCouchRepository;
import com.paizo.monitorapi.repository.SmartWatchRepository;
import com.paizo.monitorapi.service.SmartCouchService;
import com.paizo.monitorapi.service.UtilityService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * Test class used during development only to generate data
 */
@Slf4j
@SpringBootTest(classes = MonitorAPIApplication.class)
@RunWith(SpringRunner.class)
public class TestX {

    @Autowired
    private RefrigeratorRepository refrigeratorRepository;

    @Autowired
    private SmartCouchRepository smartCouchRepository;

    @Autowired
    private SmartWatchRepository smartWatchRepository;

    @Autowired
    private CarFuelRepository carFuelRepository;

    @Autowired
    private UtilityService utilityService;

    @Autowired
    private TestUtils testUtils;

//    @Before
//    public void setup() {
//        refrigeratorRepository.deleteAll();
//    }


    @Test
    public void dbInit() {
        smartCouchRepository.deleteAll();
        smartWatchRepository.deleteAll();
        carFuelRepository.deleteAll();
        refrigeratorRepository.deleteAll();
        for (int i = 0; i < 5; i++) {
            smartCouchRepository.save(testUtils.generateSmartCouchReadings());
            smartWatchRepository.save(testUtils.generateSmartWatchReadings());
            carFuelRepository.save(testUtils.generateCarFuelReadings());
            refrigeratorRepository.save(testUtils.generateRefrigeratorReadings());
        }
    }

    @Test
    @Ignore
    public void test() {
        DecimalSensor sensor1 = DecimalSensor.
                builder()
                .sensorType(SensorType.TEMPERATURE)
                .name(RandomStringUtils.randomAlphabetic(3,10))
                .value(
                    utilityService.createBigDecimalInstance(
                        RandomUtils.nextDouble(0, 99) - RandomUtils.nextDouble(0, 99)
                    )
                )
                .time(LocalDateTime.now())
                .build();

        DecimalSensor sensor2 = DecimalSensor.
                builder()
                .sensorType(SensorType.TEMPERATURE)
                .name(RandomStringUtils.randomAlphabetic(3,10))
                .value(
                    utilityService.createBigDecimalInstance(
                        RandomUtils.nextDouble(0, 99) - RandomUtils.nextDouble(0, 99)
                    )
                )
                .time(LocalDateTime.now())
                .build();

        Refrigerator refrigerator = Refrigerator
                .builder()
                .deviceId(RandomStringUtils.randomAlphabetic(3,10))
                .macAddress(RandomStringUtils.randomAlphabetic(3,10))
                .time(LocalDateTime.now())
                .vendor(RandomStringUtils.randomAlphabetic(3,10))
                .sensorsReadings(Arrays.asList(sensor1, sensor2))
                .build();

        refrigeratorRepository.save(refrigerator);

        List<Refrigerator> all = refrigeratorRepository.findAll();
        for (Refrigerator r: all) {
            log.error(r.toString());
        }
    }

    @Autowired
    SmartCouchService smartCouchService;

    @Test
    public void asd() {
        smartCouchService.averageTemperature();
    }

}
