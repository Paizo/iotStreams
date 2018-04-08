package com.paizo.monitorapi.service.impl;

import com.paizo.monitorapi.model.QSensor;
import com.paizo.monitorapi.model.QSmartCouch;
import com.paizo.monitorapi.repository.SmartCouchRepository;
import com.paizo.monitorapi.repository.SmartWatchRepository;
import com.paizo.monitorapi.service.SmartCouchService;
import com.querydsl.core.types.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@Slf4j
public class SmartCouchServiceImpl implements SmartCouchService {

    @Autowired
    private SmartCouchRepository smartCouchRepository;

    @Override
    public BigDecimal averageTemperature() {
        QSmartCouch qCouch = QSmartCouch.smartCouch;
        Predicate predicate = qCouch.deviceId.isNotNull();
        smartCouchRepository.findAll(predicate);
        return null;
    }

//    @Override
//    public BigDecimal averageTemperature() {
//        QSmartCouch qCouch = QSmartCouch.smartCouch;
//        Predicate predicate = qCouch.deviceId.isNotNull();
//        smartCouchRepository.findAll(predicate);
//        return null;
//    }
}
