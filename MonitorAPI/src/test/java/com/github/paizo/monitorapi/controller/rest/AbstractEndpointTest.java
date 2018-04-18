package com.github.paizo.monitorapi.controller.rest;

import com.github.paizo.monitorapi.model.rest.ReadingType;
import com.github.paizo.monitorapi.model.rest.SensorReading;
import com.github.paizo.monitorapi.model.rest.SingleSensorReading;
import com.github.paizo.monitorapi.service.impl.IOTDeviceServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest
public abstract class AbstractEndpointTest<T> {

    public static final String DATE = "2018-04-21T15:52:00";
    public static final String DEVICE_ID = "a";
    public static final String SENSOR_ID = "b";
    public static final String SENSOR_ID2 = "b2";

    @Autowired
    private MockMvc mvc;

    @MockBean
    IOTDeviceServiceImpl<T> service;

    String endpoint;

    @Test
    @WithMockUser
    public void avgEndpointShouldReturnCorrectValues() throws Exception {
        //GIVEN
        when(service.averageSensorValueByDeviceIdSensorIdAndDateRange(eq(DEVICE_ID),eq(SENSOR_ID), any(), any()))
                .thenReturn(
                        generateReading(ReadingType.AVERAGE, SENSOR_ID)
        );

        //DO & VERIFY
        mvc.perform(get(format("/%s/%s/%s/avg", endpoint, DEVICE_ID, SENSOR_ID))
                .param("from", DATE)
                .param("to", DATE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId", is(DEVICE_ID)))
                .andExpect(jsonPath("$.sensorReading.readingType", is(ReadingType.AVERAGE.name())))
                .andExpect(jsonPath("$.sensorReading.sensorId", is(SENSOR_ID)))
                .andExpect(jsonPath("$.sensorReading.value", is(1)));
    }

    @Test
    @WithMockUser
    public void minEndpointShouldReturnCorrectValues() throws Exception {
        //GIVEN
        when(service.minSensorValueByDeviceIdSensorIdAndDateRange(eq(DEVICE_ID),eq(SENSOR_ID), any(), any()))
                .thenReturn(
                        generateReading(ReadingType.MIN, SENSOR_ID)
        );

        //DO & VERIFY
        mvc.perform(get(format("/%s/%s/%s/min", endpoint, DEVICE_ID, SENSOR_ID))
                .param("from", DATE)
                .param("to", DATE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId", is(DEVICE_ID)))
                .andExpect(jsonPath("$.sensorReading.readingType", is(ReadingType.MIN.name())))
                .andExpect(jsonPath("$.sensorReading.sensorId", is(SENSOR_ID)))
                .andExpect(jsonPath("$.sensorReading.value", is(1)));
    }

    @Test
    @WithMockUser
    public void maxEndpointShouldReturnCorrectValues() throws Exception {
        //GIVEN
        when(service.maxSensorValueByDeviceIdSensorIdAndDateRange(eq(DEVICE_ID),eq(SENSOR_ID), any(), any()))
                .thenReturn(
                        generateReading(ReadingType.MAX, SENSOR_ID)
                );

        //DO & VERIFY
        mvc.perform(get(format("/%s/%s/%s/max", endpoint, DEVICE_ID, SENSOR_ID))
                .param("from", DATE)
                .param("to", DATE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId", is(DEVICE_ID)))
                .andExpect(jsonPath("$.sensorReading.readingType", is(ReadingType.MAX.name())))
                .andExpect(jsonPath("$.sensorReading.sensorId", is(SENSOR_ID)))
                .andExpect(jsonPath("$.sensorReading.value", is(1)));
    }

    @Test
    @WithMockUser
    public void medianEndpointShouldReturnCorrectValues() throws Exception {
        //GIVEN
        when(service.medianSensorValueByDeviceIdSensorIdAndDateRange(eq(DEVICE_ID),eq(SENSOR_ID), any(), any()))
                .thenReturn(
                        generateReading(ReadingType.MEDIAN, SENSOR_ID)
                );

        //DO & VERIFY
        mvc.perform(get(format("/%s/%s/%s/median", endpoint, DEVICE_ID, SENSOR_ID))
                .param("from", DATE)
                .param("to", DATE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId", is(DEVICE_ID)))
                .andExpect(jsonPath("$.sensorReading.readingType", is(ReadingType.MEDIAN.name())))
                .andExpect(jsonPath("$.sensorReading.sensorId", is(SENSOR_ID)))
                .andExpect(jsonPath("$.sensorReading.value", is(1)));
    }

    @Test
    @WithMockUser
    public void mediansEndpointShouldReturnCorrectValuesWithMultipleSensors() throws Exception {
        //GIVEN
        List<SingleSensorReading> sensorsReading = new ArrayList<>();
        sensorsReading.add(generateReading(ReadingType.MEDIAN, SENSOR_ID));
        sensorsReading.add(generateReading(ReadingType.MEDIAN, SENSOR_ID2));

        when(
            service.medianReadings4DeviceIdSensorIdsAndDateRange(
                eq(DEVICE_ID), any(), any(), any()
            )
        )
            .thenReturn(
                sensorsReading
        );

        //DO & VERIFY
        executeAndVerifyMultipleReadings(
                format("/%s/%s/%s,%s/medians", endpoint, DEVICE_ID, SENSOR_ID, SENSOR_ID2 ),
                ReadingType.MEDIAN
        );
    }

    @Test
    @WithMockUser
    public void averagesEndpointShouldReturnCorrectValuesWithMultipleSensors() throws Exception {
        //GIVEN
        List<SingleSensorReading> sensorsReading = new ArrayList<>();
        sensorsReading.add(generateReading(ReadingType.AVERAGE, SENSOR_ID));
        sensorsReading.add(generateReading(ReadingType.AVERAGE, SENSOR_ID2));

        when(
            service.averageReadings4DeviceIdSensorIdsAndDateRange(
                eq(DEVICE_ID), any(), any(), any()
            )
        )
            .thenReturn(
                sensorsReading
        );

        //DO & VERIFY
        executeAndVerifyMultipleReadings(
                format("/%s/%s/%s,%s/averages", endpoint, DEVICE_ID, SENSOR_ID, SENSOR_ID2 ),
                ReadingType.AVERAGE
        );
    }

    @Test
    @WithMockUser
    public void minsEndpointShouldReturnCorrectValuesWithMultipleSensors() throws Exception {
        //GIVEN
        List<SingleSensorReading> sensorsReading = new ArrayList<>();
        sensorsReading.add(generateReading(ReadingType.MIN, SENSOR_ID));
        sensorsReading.add(generateReading(ReadingType.MIN, SENSOR_ID2));

        when(
                service.minReadings4DeviceIdSensorIdsAndDateRange(
                        eq(DEVICE_ID), any(), any(), any()
                )
        )
                .thenReturn(
                        sensorsReading
                );

        //DO & VERIFY
        executeAndVerifyMultipleReadings(
                format("/%s/%s/%s,%s/mins", endpoint, DEVICE_ID, SENSOR_ID, SENSOR_ID2 ),
                ReadingType.MIN
        );
    }

    @Test
    @WithMockUser
    public void maxsEndpointShouldReturnCorrectValuesWithMultipleSensors() throws Exception {
        //GIVEN
        List<SingleSensorReading> sensorsReading = new ArrayList<>();
        sensorsReading.add(generateReading(ReadingType.MAX, SENSOR_ID));
        sensorsReading.add(generateReading(ReadingType.MAX, SENSOR_ID2));

        when(
                service.maxReadings4DeviceIdSensorIdsAndDateRange(
                        eq(DEVICE_ID), any(), any(), any()
                )
        )
                .thenReturn(
                        sensorsReading
                );

        //DO & VERIFY
        executeAndVerifyMultipleReadings(
                format("/%s/%s/%s,%s/maxs", endpoint, DEVICE_ID, SENSOR_ID, SENSOR_ID2 ),
                ReadingType.MAX
        );
    }

    @Test
    @WithMockUser
    public void mediansEndpointShouldReturnCorrectValuesWithSingleSensor() throws Exception {
        //GIVEN
        List<SingleSensorReading> sensorsReading = new ArrayList<>();
        sensorsReading.add(generateReading(ReadingType.MEDIAN, SENSOR_ID));

        when(
                service.medianReadings4DeviceIdSensorIdsAndDateRange(
                        eq(DEVICE_ID), any(), any(), any()
                )
        )
                .thenReturn(
                        sensorsReading
                );

        //DO & VERIFY
        executeAndVerifyMultipleReadingsWithSingleResult(
                format("/%s/%s/%s/medians", endpoint, DEVICE_ID, SENSOR_ID),
                ReadingType.MEDIAN
        );
    }

    @Test
    @WithMockUser
    public void minsEndpointShouldReturnCorrectValuesWithSingleSensor() throws Exception {
        //GIVEN
        List<SingleSensorReading> sensorsReading = new ArrayList<>();
        sensorsReading.add(generateReading(ReadingType.MIN, SENSOR_ID));

        when(
                service.minReadings4DeviceIdSensorIdsAndDateRange(
                        eq(DEVICE_ID), any(), any(), any()
                )
        )
                .thenReturn(
                        sensorsReading
                );

        //DO & VERIFY
        executeAndVerifyMultipleReadingsWithSingleResult(
                format("/%s/%s/%s/mins", endpoint, DEVICE_ID, SENSOR_ID),
                ReadingType.MIN
        );
    }

    @Test
    @WithMockUser
    public void maxsEndpointShouldReturnCorrectValuesWithSingleSensor() throws Exception {
        //GIVEN
        List<SingleSensorReading> sensorsReading = new ArrayList<>();
        sensorsReading.add(generateReading(ReadingType.MAX, SENSOR_ID));

        when(
                service.maxReadings4DeviceIdSensorIdsAndDateRange(
                        eq(DEVICE_ID), any(), any(), any()
                )
        )
                .thenReturn(
                        sensorsReading
                );

        //DO & VERIFY
        executeAndVerifyMultipleReadingsWithSingleResult(
                format("/%s/%s/%s/maxs", endpoint, DEVICE_ID, SENSOR_ID),
                ReadingType.MAX
        );
    }

    @Test
    @WithMockUser
    public void averagesEndpointShouldReturnCorrectValuesWithSingleSensor() throws Exception {
        //GIVEN
        List<SingleSensorReading> sensorsReading = new ArrayList<>();
        sensorsReading.add(generateReading(ReadingType.AVERAGE, SENSOR_ID));

        when(
                service.averageReadings4DeviceIdSensorIdsAndDateRange(
                        eq(DEVICE_ID), any(), any(), any()
                )
        )
                .thenReturn(
                        sensorsReading
                );

        //DO & VERIFY
        executeAndVerifyMultipleReadingsWithSingleResult(
                format("/%s/%s/%s/averages", endpoint, DEVICE_ID, SENSOR_ID),
                ReadingType.AVERAGE
        );
    }

    private SingleSensorReading generateReading(ReadingType readingType, String sensorId) {
        return SingleSensorReading
                .builder()
                .deviceId(DEVICE_ID)
                .sensorReading(
                        SensorReading
                        .builder()
                        .readingType(readingType)
                        .sensorId(sensorId)
                        .value(BigDecimal.ONE)
                        .build()
                )
                .build();
    }

    private void executeAndVerifyMultipleReadings(String url, ReadingType readingType) throws Exception {
        mvc.perform(get(url)
                .param("from", DATE)
                .param("to", DATE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].deviceId", is(DEVICE_ID)))
                .andExpect(jsonPath("$[0].sensorReading.readingType", is(readingType.name())))
                .andExpect(jsonPath("$[0].sensorReading.sensorId", is(SENSOR_ID)))
                .andExpect(jsonPath("$[0].sensorReading.value", is(1)))
                .andExpect(jsonPath("$[1].deviceId", is(DEVICE_ID)))
                .andExpect(jsonPath("$[1].sensorReading.readingType", is(readingType.name())))
                .andExpect(jsonPath("$[1].sensorReading.sensorId", is(SENSOR_ID2)))
                .andExpect(jsonPath("$[1].sensorReading.value", is(1)));
    }

    private void executeAndVerifyMultipleReadingsWithSingleResult(String url, ReadingType readingType) throws Exception {
        mvc.perform(get(url)
                .param("from", DATE)
                .param("to", DATE)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].deviceId", is(DEVICE_ID)))
                .andExpect(jsonPath("$[0].sensorReading.readingType", is(readingType.name())))
                .andExpect(jsonPath("$[0].sensorReading.sensorId", is(SENSOR_ID)))
                .andExpect(jsonPath("$[0].sensorReading.value", is(1)));
    }
}
