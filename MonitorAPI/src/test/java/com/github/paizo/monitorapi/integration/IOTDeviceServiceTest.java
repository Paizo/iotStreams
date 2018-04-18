package com.github.paizo.monitorapi.integration;

import com.github.paizo.monitorapi.MonitorAPIApplication;
import com.github.paizo.monitorapi.model.Refrigerator;
import com.github.paizo.monitorapi.model.rest.ReadingType;
import com.github.paizo.monitorapi.model.rest.SingleSensorReading;
import com.github.paizo.monitorapi.repository.RefrigeratorRepository;
import com.github.paizo.monitorapi.service.impl.IOTDeviceServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

@Slf4j
@SpringBootTest(classes = MonitorAPIApplication.class)
@RunWith(SpringRunner.class)
public class IOTDeviceServiceTest {

    @Autowired
    RefrigeratorRepository refrigeratorRepository;

    @Autowired
    IOTDeviceServiceImpl<Refrigerator> iotDeviceService;

    private final Date date = new Date();
    private final String deviceId = "myId";
    private final String sensorId = "temp";


    @Before
    public void setup() {
        refrigeratorRepository.deleteAll();
    }

    @Test
    public void averageSensorValueByDeviceIdAndDateRangeShouldReturnCorrectAverage() {
        double expectedAvg = generateAndSaveDocumentsAndReturnTheirAvg(RandomUtils.nextInt(3,10));
        SingleSensorReading singleSensorReading = iotDeviceService.averageSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, date, DateUtils.addDays(date, 10));
        assertResult(singleSensorReading, expectedAvg, deviceId, sensorId, ReadingType.AVERAGE);
    }

    @Test
    public void minSensorValueByDeviceIdSensorIdAndDateRangeReturnCorrectMin() {
        double expectedMin = generateAndSaveDocumentsAndReturnTheirMinOrMax(RandomUtils.nextInt(3,10), false);
        SingleSensorReading singleSensorReading = iotDeviceService.minSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, date, DateUtils.addDays(date, 10));
        assertResult(singleSensorReading, expectedMin, deviceId, sensorId, ReadingType.MIN);
    }

    @Test
    public void maxSensorValueByDeviceIdSensorIdAndDateRangeReturnCorrectMax() {
        double expectedMin = generateAndSaveDocumentsAndReturnTheirMinOrMax(RandomUtils.nextInt(3,10), true);
        SingleSensorReading singleSensorReading = iotDeviceService.maxSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, date, DateUtils.addDays(date, 10));
        assertResult(singleSensorReading, expectedMin, deviceId, sensorId, ReadingType.MAX);
    }

    @Test
    public void medianSensorValueByDeviceIdAndDateRangeShouldReturnCorrectMedianIfOddCount() {
        double expectedMedian = generateAndSaveDocumentsAndReturnTheMedian(true);
        SingleSensorReading singleSensorReading = iotDeviceService.medianSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, date, DateUtils.addDays(date, 10));
        assertResult(singleSensorReading, expectedMedian, deviceId, sensorId, ReadingType.MEDIAN);
    }

    @Test
    public void medianSensorValueByDeviceIdAndDateRangeShouldReturnCorrectMedianIfEvenCount() {
        double expectedMedian = generateAndSaveDocumentsAndReturnTheMedian(false);
        SingleSensorReading singleSensorReading = iotDeviceService.medianSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, date, DateUtils.addDays(date, 10));
        assertResult(singleSensorReading, expectedMedian, deviceId, sensorId, ReadingType.MEDIAN);
    }

    private void assertResult(SingleSensorReading result, double expectedResult, String expectedDeviceId, String expectedSensorId, ReadingType expectedReadingType) {
        assertThat(result, is(notNullValue()));
        assertThat(result.getDeviceId(), is(expectedDeviceId));
        assertThat(result.getSensorReading(), is(notNullValue()));
        assertThat(result.getSensorReading().getReadingType(), is(expectedReadingType));
        assertThat(result.getSensorReading().getSensorId(), is(expectedSensorId));
        assertThat(result.getSensorReading().getValue(), notNullValue());
        assertThat(result.getSensorReading().getValue().doubleValue(), is(expectedResult));
    }

    private double generateAndSaveDocumentsAndReturnTheirAvg(int number) {
        int sensorReadingSum = 0;
        for (int i = 0; i < number; i++) {
            int sensorReading = RandomUtils.nextInt(0, 100);
            sensorReadingSum += sensorReading;
            Refrigerator refrigerator = Refrigerator
                    .builder()
                    .deviceId("myId")
                    .time(DateUtils.addDays(date, RandomUtils.nextInt(1, 9)))
                    .sensorId("temp")
                    .sensorValue(sensorReading)
                    .build();
            refrigeratorRepository.save(refrigerator);
        }
        return (0.0  + sensorReadingSum) / number;
    }

    private double generateAndSaveDocumentsAndReturnTheirMinOrMax(int number, boolean returnMax) {
        int[] sensorValues = new int[number];
        for (int i = 0; i < number; i++) {
            int sensorReading = RandomUtils.nextInt(0, 100);
            sensorValues[i] = sensorReading;
            Refrigerator refrigerator = Refrigerator
                    .builder()
                    .deviceId("myId")
                    .time(DateUtils.addDays(date, RandomUtils.nextInt(1, 9)))
                    .sensorId("temp")
                    .sensorValue(sensorReading)
                    .build();
            refrigeratorRepository.save(refrigerator);
        }
        Arrays.sort(sensorValues);
        return returnMax ? sensorValues[sensorValues.length -1] : sensorValues[0];
    }

    private double generateAndSaveDocumentsAndReturnTheMedian(boolean odd) {
        int count = odd ? 5 : 6;
        List<Integer> sensorReadings = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            int sensorReading = RandomUtils.nextInt(0, 100);
            sensorReadings.add(sensorReading);
            Refrigerator refrigerator = Refrigerator
                    .builder()
                    .deviceId("myId")
                    .time(DateUtils.addDays(date, RandomUtils.nextInt(1, 9)))
                    .sensorId("temp")
                    .sensorValue(sensorReading)
                    .build();
            refrigeratorRepository.save(refrigerator);
        }
        Integer[] values = sensorReadings.toArray(new Integer[sensorReadings.size()]);
        Arrays.sort(values);
        if (log.isDebugEnabled()) {
            for (Integer i : values) {
                log.debug(format("ordered sensor value [%d]", i));
            }
        }
        if (odd) {
            double median = values[values.length / 2];
            log.debug("odd median " + median);
            return median;
        } else {
            double val1 = values[values.length / 2];
            double val2 = values[values.length / 2 - 1];
            double median = (val1 + val2) / 2;
            log.debug("median " + val1 + ", " + val2 + " = " + median);
            return median;
        }
    }

}
