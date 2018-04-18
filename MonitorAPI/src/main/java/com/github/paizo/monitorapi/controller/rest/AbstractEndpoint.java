package com.github.paizo.monitorapi.controller.rest;

import com.github.paizo.monitorapi.model.rest.SingleSensorReading;
import com.github.paizo.monitorapi.service.IOTDeviceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

import static java.lang.String.format;

@Api
@Slf4j
public abstract class AbstractEndpoint {

    IOTDeviceService iotService;

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
        SingleSensorReading singleSensorReading = iotService.averageSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, from, to);
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
        SingleSensorReading singleSensorReading = iotService.maxSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, from, to);
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
        SingleSensorReading singleSensorReading = iotService.minSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, from, to);
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
        SingleSensorReading singleSensorReading = iotService.medianSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, from, to);
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
        List<SingleSensorReading> singleSensorReading = iotService.medianReadings4DeviceIdSensorIdsAndDateRange(deviceId, from, to, sensorIds);
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
        List<SingleSensorReading> singleSensorReading = iotService.averageReadings4DeviceIdSensorIdsAndDateRange(deviceId, from, to, sensorIds);
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
        List<SingleSensorReading> singleSensorReading = iotService.minReadings4DeviceIdSensorIdsAndDateRange(deviceId, from, to, sensorIds);
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
        List<SingleSensorReading> singleSensorReading = iotService.maxReadings4DeviceIdSensorIdsAndDateRange(deviceId, from, to, sensorIds);
        return singleSensorReading;
    }

}
