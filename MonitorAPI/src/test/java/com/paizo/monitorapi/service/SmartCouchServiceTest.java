package com.paizo.monitorapi.service;

import com.paizo.monitorapi.MonitorAPIApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest(classes = MonitorAPIApplication.class)
@RunWith(SpringRunner.class)
public class SmartCouchServiceTest {

    @Autowired
    SmartCouchService smartCouchService;

    @Test
    public void average() {
        smartCouchService.averageTemperature();
    }
}
