package com.github.paizo.monitorapi.integration;

import com.github.paizo.monitorapi.MonitorAPIApplication;
import com.github.paizo.monitorapi.model.Refrigerator;
import com.github.paizo.monitorapi.model.projection.AverageTemperature;
import com.github.paizo.monitorapi.model.projection.Median;
import com.github.paizo.monitorapi.repository.RefrigeratorRepository;
import com.github.paizo.monitorapi.service.RefrigeratorService;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.assertThat;

@SpringBootTest(classes = MonitorAPIApplication.class)
@RunWith(SpringRunner.class)
public class RefrigeratorServiceTest {

    @Autowired
    RefrigeratorRepository refrigeratorRepository;

    @Autowired
    RefrigeratorService refrigeratorService;

    final Date date = new Date();

    private Refrigerator refrigerator1;
    private Refrigerator refrigerator2;
    private Refrigerator refrigerator3;

    @Before
    public void setup() {
        refrigeratorRepository.deleteAll();
        refrigerator1 = Refrigerator
                .builder()
                .deviceId("myId")
                .time(DateUtils.addDays(date, 1))
                .temperature(5)
                .build();
        refrigerator2 = Refrigerator
                .builder()
                .deviceId("myId")
                .time(DateUtils.addDays(date, 2))
                .temperature(6)
                .build();
        refrigerator3 = Refrigerator
                .builder()
                .deviceId("myId")
                .time(DateUtils.addDays(date, 3))
                .temperature(4)
                .build();
        refrigeratorRepository.save(refrigerator1);
        refrigeratorRepository.save(refrigerator2);
        refrigeratorRepository.save(refrigerator3);
    }

    @Test
    public void averageTemperatureByDeviceIdShouldReturnCorrectAverage() {
        AverageTemperature avg = refrigeratorService.averageTemperatureByDeviceId("myId");
        assertThat(avg, is(notNullValue()));
        assertThat(avg.getAverageTemperature(), equalTo(new BigDecimal(5).setScale(1)));
        assertThat(avg.getDeviceId(), is(refrigerator1.getDeviceId()));
    }

    @Test
    public void average() {
        AverageTemperature avg = refrigeratorService.averageTemperatureByDeviceIdAndDateRange("myId", date, DateUtils.addDays(date, 5));
        assertThat(avg, is(notNullValue()));
        assertThat(avg.getAverageTemperature(), equalTo(new BigDecimal(5).setScale(1)));
        assertThat(avg.getDeviceId(), is(refrigerator1.getDeviceId()));
    }

    @Test
    public void median() {
        Median avg = refrigeratorService.medianTemperature();
        //TODO
    }

}
