package com.paizo.monitorapi;

import com.paizo.monitorapi.repository.CarFuelRepository;
import com.paizo.monitorapi.repository.RefrigeratorRepository;
import com.paizo.monitorapi.repository.SmartCouchRepository;
import com.paizo.monitorapi.repository.SmartWatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
    private TestUtils testUtils;

    @Before
    public void setup() {
        smartCouchRepository.deleteAll();
        smartWatchRepository.deleteAll();
        carFuelRepository.deleteAll();
        refrigeratorRepository.deleteAll();
    }


    @Test
    public void dbInit() {
        for (int i = 0; i < 5; i++) {
            smartCouchRepository.save(testUtils.generateSmartCouchReadings("couch" + i));
            smartWatchRepository.save(testUtils.generateSmartWatchReadings("watch" + i));
            carFuelRepository.save(testUtils.generateCarFuelReadings("car" + i));
            refrigeratorRepository.save(testUtils.generateRefrigeratorReadings("refrigerator" + i));
        }
    }

}
