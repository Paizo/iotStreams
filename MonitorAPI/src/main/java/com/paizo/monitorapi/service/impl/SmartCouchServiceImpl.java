package com.paizo.monitorapi.service.impl;

import com.paizo.monitorapi.model.SmartCouch;
import com.paizo.monitorapi.repository.SmartCouchRepository;
import com.paizo.monitorapi.service.SmartCouchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
@Slf4j
public class SmartCouchServiceImpl implements SmartCouchService {

    @Autowired
    private SmartCouchRepository smartCouchRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public BigDecimal averageTemperature() {
//        final Aggregation aggregation = newAggregation(
//                match(new Criteria("deviceId").is("ABYbEVJ")),
////                project("_id", "deviceId").and("sensorsReadings"),
//                unwind("sensorsReadings"),
//                group("sensorsReadings.sensorType")
////                    .last("sensorType").as("sensorType")
////                    .addToSet("id").as("ids")
//                    .avg("sensorsReadings.value").as("averageTemperature")

//        final Aggregation aggregation = newAggregation(
//                match(new Criteria("deviceId").is("ABYbEVJ")),
//                unwind("sensorsReadings"),
//                match(new Criteria("sensorsReadings.sensorType").is(SensorType.TEMPERATURE.name())),
//                group("sensorsReadings.value").avg("value").as("val")
//        );

        final Aggregation aggregation = newAggregation(
                match(
                    new Criteria("deviceId")
                            .is("ABYbEVJ")
                ),
                group("deviceId").avg("temperature").as("val"),
                project("val")
        );

        AggregationResults<SmartCouch> result = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(SmartCouch.class), SmartCouch.class);
        //result.getMappedResults();
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
