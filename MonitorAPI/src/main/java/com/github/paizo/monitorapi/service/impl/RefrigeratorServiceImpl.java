package com.github.paizo.monitorapi.service.impl;

import com.github.paizo.monitorapi.model.Refrigerator;
import com.github.paizo.monitorapi.model.projection.AverageTemperature;
import com.github.paizo.monitorapi.service.RefrigeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;

@Service
public class RefrigeratorServiceImpl implements RefrigeratorService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public AverageTemperature averageTemperature() {
        final Aggregation aggregation = newAggregation(
                match(
                        new Criteria("deviceId")
                                .is("refrigerator")
                ),
                group("deviceId")
                        .avg("temperature").as("averageTemperature")
                        .addToSet("deviceId").as("deviceId"),
                project("deviceId", "averageTemperature")
        );

        AggregationResults<AverageTemperature> result = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Refrigerator.class), AverageTemperature.class);
        List<AverageTemperature> mappedResults = result.getMappedResults();
        return (mappedResults != null ? mappedResults.get(0) : null);
    }

    @Override
    public AverageTemperature averageTemperatureByDeviceId(String deviceId) {
        final Aggregation aggregation = newAggregation(
                match(
                        new Criteria("deviceId")
                                .is(deviceId)
                ),
                group("deviceId")
                        .avg("temperature").as("averageTemperature")
                        .addToSet("deviceId").as("deviceId"),
                project("deviceId", "averageTemperature")
        );

        AggregationResults<AverageTemperature> result = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Refrigerator.class), AverageTemperature.class);
        List<AverageTemperature> mappedResults = result.getMappedResults();
        return (mappedResults != null ? mappedResults.get(0) : null);
    }

}
