package com.github.paizo.monitorapi.controller.rest;

import com.github.paizo.monitorapi.model.rest.SingleSensorReading;
import com.github.paizo.monitorapi.service.RefrigeratorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

import static java.lang.String.format;

@RestController
@RequestMapping(value = "/refrigerator", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api
@Slf4j
public class RefrigeratorEndpoint {

    @Autowired
    private RefrigeratorService refrigeratorService;

    final String dateTimeFormat = "yyyy-MM-dd'T'HH:mm:ss";

    @ApiOperation(
            value = "Returns the AVERAGE sensor reading for the given Device and Sensor Id",
            notes = "Date time format is yyyy-MM-dd'T'HH:mm:ss ie: 2018-04-21T15:52:00",
            response = SingleSensorReading.class
    )
    @GetMapping(value = "/{deviceId}/{sensorId}/avg")
    public SingleSensorReading getAverageSensorReading(
                        @ApiParam(value = "Device ID") @PathVariable("deviceId") String deviceId,
                        @ApiParam(value = "Sensor ID") @PathVariable("sensorId") String sensorId,
                        @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "Starting date") @RequestParam(value = "from") Date from,
                        @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "End date") @RequestParam(value = "to") Date to
    ) {
        log.trace(format("getAverageSensorReading with parameters deviceId[%s] sensorId[%s] from[%s] to[%s]", deviceId, sensorId, from.toString(), to.toString()));
        SingleSensorReading singleSensorReading = refrigeratorService.averageSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, from, to);
        return singleSensorReading;
    }

    @ApiOperation(
            value = "Returns the MAX sensor reading for the given Device and Sensor Id",
            notes = "Date time format is yyyy-MM-dd'T'HH:mm:ss ie: 2018-04-21T15:52:00",
            response = SingleSensorReading.class
    )
    @GetMapping(value = "/{deviceId}/{sensorId}/max")
    public SingleSensorReading getMaxSensorReading(
                        @ApiParam(value = "Device ID") @PathVariable("deviceId") String deviceId,
                        @ApiParam(value = "Sensor ID") @PathVariable("sensorId") String sensorId,
                        @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "Starting date") @RequestParam(value = "from") Date from,
                        @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "End date") @RequestParam(value = "to") Date to
    ) {
        log.trace(format("getMaxSensorReading with parameters deviceId[%s] sensorId[%s] from[%s] to[%s]", deviceId, sensorId, from.toString(), to.toString()));
        SingleSensorReading singleSensorReading = refrigeratorService.maxSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, from, to);
        return singleSensorReading;
    }

    @ApiOperation(
            value = "Returns the MIN sensor reading for the given Device and Sensor Id",
            notes = "Date time format is yyyy-MM-dd'T'HH:mm:ss ie: 2018-04-21T15:52:00",
            response = SingleSensorReading.class
    )
    @GetMapping(value = "/{deviceId}/{sensorId}/min")
    public SingleSensorReading getMinSensorReading(
                        @ApiParam(value = "Device ID") @PathVariable("deviceId") String deviceId,
                        @ApiParam(value = "Sensor ID") @PathVariable("sensorId") String sensorId,
                        @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "Starting date") @RequestParam(value = "from") Date from,
                        @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "End date") @RequestParam(value = "to") Date to
    ) {
        log.trace(format("getMinSensorReading with parameters deviceId[%s] sensorId[%s] from[%s] to[%s]", deviceId, sensorId, from.toString(), to.toString()));
        SingleSensorReading singleSensorReading = refrigeratorService.minSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, from, to);
        return singleSensorReading;
    }

    @ApiOperation(
            value = "Returns the MEDIAN sensor reading for the given Device and Sensor Id",
            notes = "Date time format is yyyy-MM-dd'T'HH:mm:ss ie: 2018-04-21T15:52:00",
            response = SingleSensorReading.class
    )
    @GetMapping(value = "/{deviceId}/{sensorId}/median")
    public SingleSensorReading getMedianSensorReading(
                        @ApiParam(value = "Device ID") @PathVariable("deviceId") String deviceId,
                        @ApiParam(value = "Sensor ID") @PathVariable("sensorId") String sensorId,
                        @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "Starting date") @RequestParam(value = "from") Date from,
                        @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "End date") @RequestParam(value = "to") Date to
    ) {
        log.trace(format("getMedianSensorReading with parameters deviceId[%s] sensorId[%s] from[%s] to[%s]", deviceId, sensorId, from.toString(), to.toString()));
        SingleSensorReading singleSensorReading = refrigeratorService.medianSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, from, to);
        return singleSensorReading;
    }

    @ApiOperation(
            value = "Returns the MEDIANs of sensors reading for the given Device and Sensor Ids",
            notes = "Date time format is yyyy-MM-dd'T'HH:mm:ss ie: 2018-04-21T15:52:00",
            response = SingleSensorReading.class, responseContainer = "List"
    )
    @GetMapping(value = "/{deviceId}/{sensorIds}/medians")
    public List<SingleSensorReading> getMediansSensorsReading(
            @ApiParam(value = "Device ID") @PathVariable("deviceId") String deviceId,
            @ApiParam(value = "Sensor ID") @PathVariable("sensorIds") String[] sensorIds,
            @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "Starting date") @RequestParam(value = "from") Date from,
            @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "End date") @RequestParam(value = "to") Date to
    ) {
        log.trace(format("getMedianSensorReading with parameters deviceId[%s] sensorId[%s] from[%s] to[%s]", deviceId, sensorIds, from.toString(), to.toString()));
        List<SingleSensorReading> singleSensorReading = refrigeratorService.medianReadings4DeviceIdSensorIdsAndDateRange(deviceId, from, to, sensorIds);
        return singleSensorReading;
    }

    @ApiOperation(
            value = "Returns the AVERAGEs of sensors reading for the given Device and Sensor Ids",
            notes = "Date time format is yyyy-MM-dd'T'HH:mm:ss ie: 2018-04-21T15:52:00",
            response = SingleSensorReading.class, responseContainer = "List"
    )
    @GetMapping(value = "/{deviceId}/{sensorIds}/averages")
    public List<SingleSensorReading> getAveragesSensorsReading(
            @ApiParam(value = "Device ID") @PathVariable("deviceId") String deviceId,
            @ApiParam(value = "Sensor ID") @PathVariable("sensorIds") String[] sensorIds,
            @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "Starting date") @RequestParam(value = "from") Date from,
            @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "End date") @RequestParam(value = "to") Date to
    ) {
        log.trace(format("getAveragesSensorsReading with parameters deviceId[%s] sensorId[%s] from[%s] to[%s]", deviceId, sensorIds, from.toString(), to.toString()));
        List<SingleSensorReading> singleSensorReading = refrigeratorService.averageReadings4DeviceIdSensorIdsAndDateRange(deviceId, from, to, sensorIds);
        return singleSensorReading;
    }

    @ApiOperation(
            value = "Returns the MINs of sensors reading for the given Device and Sensor Ids",
            notes = "Date time format is yyyy-MM-dd'T'HH:mm:ss ie: 2018-04-21T15:52:00",
            response = SingleSensorReading.class, responseContainer = "List"
    )
    @GetMapping(value = "/{deviceId}/{sensorIds}/mins")
    public List<SingleSensorReading> getMinsSensorsReading(
            @ApiParam(value = "Device ID") @PathVariable("deviceId") String deviceId,
            @ApiParam(value = "Sensor ID") @PathVariable("sensorIds") String[] sensorIds,
            @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "Starting date") @RequestParam(value = "from") Date from,
            @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "End date") @RequestParam(value = "to") Date to
    ) {
        log.trace(format("getMinsSensorsReading with parameters deviceId[%s] sensorId[%s] from[%s] to[%s]", deviceId, sensorIds, from.toString(), to.toString()));
        List<SingleSensorReading> singleSensorReading = refrigeratorService.minReadings4DeviceIdSensorIdsAndDateRange(deviceId, from, to, sensorIds);
        return singleSensorReading;
    }

    @ApiOperation(
            value = "Returns the MAXs of sensors reading for the given Device and Sensor Ids",
            notes = "Date time format is yyyy-MM-dd'T'HH:mm:ss ie: 2018-04-21T15:52:00",
            response = SingleSensorReading.class, responseContainer = "List"
    )
    @GetMapping(value = "/{deviceId}/{sensorIds}/maxs")
    public List<SingleSensorReading> getMaxsSensorsReading(
            @ApiParam(value = "Device ID") @PathVariable("deviceId") String deviceId,
            @ApiParam(value = "Sensor ID") @PathVariable("sensorIds") String[] sensorIds,
            @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "Starting date") @RequestParam(value = "from") Date from,
            @DateTimeFormat(pattern = dateTimeFormat)@ApiParam(value = "End date") @RequestParam(value = "to") Date to
    ) {
        log.trace(format("getMaxsSensorsReading with parameters deviceId[%s] sensorId[%s] from[%s] to[%s]", deviceId, sensorIds, from.toString(), to.toString()));
        List<SingleSensorReading> singleSensorReading = refrigeratorService.maxReadings4DeviceIdSensorIdsAndDateRange(deviceId, from, to, sensorIds);
        return singleSensorReading;
    }

}
