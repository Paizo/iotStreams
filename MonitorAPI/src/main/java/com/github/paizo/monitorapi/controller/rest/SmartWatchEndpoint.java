package com.github.paizo.monitorapi.controller.rest;

import com.github.paizo.monitorapi.model.SmartWatch;
import com.github.paizo.monitorapi.service.impl.IOTDeviceServiceImpl;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Provide readings endpoint specific to 'smartwatch' devices
 */
@RestController
@RequestMapping(value = "/smartwatch", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api
@Slf4j
public class SmartWatchEndpoint extends AbstractEndpoint {

    @Autowired
    public void setIotService(IOTDeviceServiceImpl<SmartWatch> service) {
        this.iotService = service;
    }

}