package com.github.paizo.monitorapi.service.impl;

import com.github.paizo.monitorapi.model.Refrigerator;
import com.github.paizo.monitorapi.model.projection.AverageTemperature;
import com.github.paizo.monitorapi.model.projection.Median;
import com.github.paizo.monitorapi.repository.RefrigeratorRepository;
import com.github.paizo.monitorapi.service.RefrigeratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Ceil;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Floor;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.ArrayElemAt;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Ceil.ceilValueOf;
import static org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Floor.floorValueOf;

@Service
public class RefrigeratorServiceImpl implements RefrigeratorService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    RefrigeratorRepository refrigeratorRepository;

    //TODO group by temperature only
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
                    Criteria.where("deviceId")
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

    @Override
    public AverageTemperature averageTemperatureByDeviceIdAndDateRange(String deviceId, Date from, Date to) {
        final Aggregation aggregation = newAggregation(
                match(
                    Criteria.where("deviceId")
                        .is(deviceId)
                        .andOperator(
                            Criteria.where("time").gte(from),
                            Criteria.where("time").lte(to)
                        )
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

    //see https://www.compose.com/articles/mongo-metrics-finding-a-happy-median/
    @Override
    public Median medianTemperature() {
        Floor floor = floorValueOf("midpoint");
        Ceil ceil = ceilValueOf("midpoint");
        ArrayElemAt beginValue = ArrayElemAt.arrayOf("temperatures").elementAt("high");
        ArrayElemAt endValue = ArrayElemAt.arrayOf("temperatures").elementAt("low");
        final Aggregation aggregation = newAggregation(
                match(
                    Criteria.where("temperature").exists(true)
                ),
                group().count().as("count").push("temperature").as("temperatures"),
                unwind("temperatures"),
                sort(Sort.Direction.ASC, "temperatures"),
                project("count", "temperatures")
                    .andExpression("count / 2").as("midpoint"),
                project("count", "temperatures", "midpoint")
                    .and(floor).as("low")
                    .and(ceil).as("high"),
                group()
                    .push("temperatures").as("temperatures")
                    .avg("high").as("high")
                    .avg("low").as("low"),
                project()
                    .and(beginValue).as("beginValue")
                    .and(endValue).as("endValue"),
                project().andExpression(" (beginValue + endValue) / 2 ").as("median")
        );

        AggregationResults<Median> result = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(Refrigerator.class), Median.class);
        List<Median> mappedResults = result.getMappedResults();
        return mappedResults == null ? null : mappedResults.get(0);
    }

}
