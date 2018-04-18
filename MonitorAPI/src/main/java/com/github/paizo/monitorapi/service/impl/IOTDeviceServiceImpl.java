package com.github.paizo.monitorapi.service.impl;

import com.github.paizo.monitorapi.model.projection.SingleValueHolder;
import com.github.paizo.monitorapi.model.rest.ReadingType;
import com.github.paizo.monitorapi.model.rest.SensorReading;
import com.github.paizo.monitorapi.model.rest.SingleSensorReading;
import com.github.paizo.monitorapi.service.IOTDeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Ceil;
import org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Floor;
import org.springframework.data.mongodb.core.aggregation.ArrayOperators.ArrayElemAt;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.*;
import static org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Ceil.ceilValueOf;
import static org.springframework.data.mongodb.core.aggregation.ArithmeticOperators.Floor.floorValueOf;

@Service
public class IOTDeviceServiceImpl<T> implements IOTDeviceService {

    public static final String DEVICE_ID_FIELD = "deviceId";
    public static final String SENSOR_ID_FIELD = "sensorId";
    public static final String SENSOR_VALUE_FIELD = "sensorValue";
    public static final String TIME_FIELD = "time";
    public static final String VALUE_PROJECTION = "value";

    private Class<T> genericType;

    @Autowired
    private MongoTemplate mongoTemplate;

    @PostConstruct
    void determineClassType() {
        if (genericType == null) {
            this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), IOTDeviceServiceImpl.class);
        }
    }

    public void setGenericType(Class<T> genericType) {
        this.genericType = genericType;
    }

    @Override
    public SingleSensorReading averageSensorValueByDeviceIdSensorIdAndDateRange(String deviceId, String sensorId, Date from, Date to) {
        verifyParameters(deviceId, from, to, sensorId);

        final Aggregation aggregation = newAggregation(
                match(
                    Criteria.where(DEVICE_ID_FIELD).is(deviceId)
                            .andOperator(
                                    Criteria.where(SENSOR_ID_FIELD).is(sensorId),
                                    Criteria.where(SENSOR_VALUE_FIELD).exists(true),
                                    Criteria.where(TIME_FIELD).gte(from),
                                    Criteria.where(TIME_FIELD).lte(to)
                            )
                ),
                group(DEVICE_ID_FIELD)
                        .avg(SENSOR_VALUE_FIELD).as(VALUE_PROJECTION),
                project(VALUE_PROJECTION)
        );

        AggregationResults<SingleValueHolder> result = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(genericType), SingleValueHolder.class);
        List<SingleValueHolder> mappedResults = result.getMappedResults();
        return extractResult(mappedResults, deviceId, sensorId, ReadingType.AVERAGE);
    }

    @Override
    public SingleSensorReading minSensorValueByDeviceIdSensorIdAndDateRange(String deviceId, String sensorId, Date from, Date to) {
        verifyParameters(deviceId, from, to, sensorId);

        final Aggregation aggregation = newAggregation(
                match(
                    Criteria.where(DEVICE_ID_FIELD).is(deviceId)
                            .andOperator(
                                    Criteria.where(SENSOR_ID_FIELD).is(sensorId),
                                    Criteria.where(SENSOR_VALUE_FIELD).exists(true),
                                    Criteria.where(TIME_FIELD).gte(from),
                                    Criteria.where(TIME_FIELD).lte(to)
                            )
                ),
                group(DEVICE_ID_FIELD)
                        .min(SENSOR_VALUE_FIELD).as(VALUE_PROJECTION),
                project(VALUE_PROJECTION)
        );

        AggregationResults<SingleValueHolder> result = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(genericType), SingleValueHolder.class);
        List<SingleValueHolder> mappedResults = result.getMappedResults();
        return extractResult(mappedResults, deviceId, sensorId, ReadingType.MIN);
    }

    @Override
    public SingleSensorReading maxSensorValueByDeviceIdSensorIdAndDateRange(String deviceId, String sensorId, Date from, Date to) {
        verifyParameters(deviceId, from, to, sensorId);

        final Aggregation aggregation = newAggregation(
                match(
                    Criteria.where(DEVICE_ID_FIELD).is(deviceId)
                            .andOperator(
                                    Criteria.where(SENSOR_ID_FIELD).is(sensorId),
                                    Criteria.where(SENSOR_VALUE_FIELD).exists(true),
                                    Criteria.where(TIME_FIELD).gte(from),
                                    Criteria.where(TIME_FIELD).lte(to)
                            )
                ),
                group(DEVICE_ID_FIELD)
                        .max(SENSOR_VALUE_FIELD).as(VALUE_PROJECTION),
                project(VALUE_PROJECTION)
        );

        AggregationResults<SingleValueHolder> result = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(genericType), SingleValueHolder.class);
        List<SingleValueHolder> mappedResults = result.getMappedResults();
        return extractResult(mappedResults, deviceId, sensorId, ReadingType.MAX);
    }

    /**
     * extracted from https://www.compose.com/articles/mongo-metrics-finding-a-happy-median/
     */
    @Override
    public SingleSensorReading medianSensorValueByDeviceIdSensorIdAndDateRange(String deviceId, String sensorId, Date from, Date to) {
        verifyParameters(deviceId, from, to, sensorId);

        final Floor floor = floorValueOf("midpoint");
        final Ceil ceil = ceilValueOf("midpoint");
        final ArrayElemAt beginValue = ArrayElemAt.arrayOf("values").elementAt("low");
        final ArrayElemAt endValue = ArrayElemAt.arrayOf("values").elementAt("high");

        final Aggregation aggregation = newAggregation(
                match(
                        Criteria.where(DEVICE_ID_FIELD).is(deviceId)
                                .andOperator(
                                        Criteria.where(SENSOR_ID_FIELD).is(sensorId),
                                        Criteria.where(SENSOR_VALUE_FIELD).exists(true),
                                        Criteria.where(TIME_FIELD).gte(from),
                                        Criteria.where(TIME_FIELD).lte(to)
                                )
                ),
                group().count().as("count").push(SENSOR_VALUE_FIELD).as("values"),
                unwind("values"),
                sort(Sort.Direction.ASC, "values"),
                project("count", "values")
                        .andExpression("(count - 1) / 2").as("midpoint"),
                project("count", "values", "midpoint")
                        .and(floor).as("low")
                        .and(ceil).as("high"),
                group()
                        .push("values").as("values")
                        .avg("high").as("high")
                        .avg("low").as("low"),
                project()
                        .and(beginValue).as("beginValue")
                        .and(endValue).as("endValue"),
                project().andExpression("(beginValue + endValue) / 2 ").as(VALUE_PROJECTION)
        );

        AggregationResults<SingleValueHolder> result = mongoTemplate.aggregate(aggregation, mongoTemplate.getCollectionName(genericType), SingleValueHolder.class);
        List<SingleValueHolder> mappedResults = result.getMappedResults();
        return extractResult(mappedResults, deviceId, sensorId, ReadingType.MEDIAN);
    }

    @Override
    public List<SingleSensorReading> averageReadings4DeviceIdSensorIdsAndDateRange(String deviceId, Date from, Date to, String... sensorsId) {
        verifyParameters(deviceId, from, to, sensorsId);

        List<SingleSensorReading> results = new ArrayList<>(sensorsId.length);
        for (String sensorId: sensorsId) {
            SingleSensorReading singleSensorReading = this.averageSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, from, to);
            results.add(singleSensorReading);
        }
        return results;
    }

    @Override
    public List<SingleSensorReading> maxReadings4DeviceIdSensorIdsAndDateRange(String deviceId, Date from, Date to, String... sensorsId) {
        verifyParameters(deviceId, from, to, sensorsId);

        List<SingleSensorReading> results = new ArrayList<>(sensorsId.length);
        for (String sensorId: sensorsId) {
            SingleSensorReading singleSensorReading = this.maxSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, from, to);
            results.add(singleSensorReading);
        }
        return results;
    }

    @Override
    public List<SingleSensorReading> minReadings4DeviceIdSensorIdsAndDateRange(String deviceId, Date from, Date to, String... sensorsId) {
        verifyParameters(deviceId, from, to, sensorsId);

        List<SingleSensorReading> results = new ArrayList<>(sensorsId.length);
        for (String sensorId: sensorsId) {
            SingleSensorReading singleSensorReading = this.minSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, from, to);
            results.add(singleSensorReading);
        }
        return results;
    }

    @Override
    public List<SingleSensorReading> medianReadings4DeviceIdSensorIdsAndDateRange(String deviceId, Date from, Date to, String... sensorsId) {
        verifyParameters(deviceId, from, to, sensorsId);

        List<SingleSensorReading> results = new ArrayList<>(sensorsId.length);
        for (String sensorId: sensorsId) {
            SingleSensorReading singleSensorReading = this.medianSensorValueByDeviceIdSensorIdAndDateRange(deviceId, sensorId, from, to);
            results.add(singleSensorReading);
        }
        return results;
    }

    /**
     * Extract the result from the aggregation to a user friendly Bean
     * @param mappedResults
     * @param deviceId
     * @param sensorId
     * @param readingType
     * @return SingleSensorReading
     */
    private SingleSensorReading extractResult(List<SingleValueHolder> mappedResults, String deviceId, String sensorId, ReadingType readingType) {
        SingleValueHolder singleValueHolder = (mappedResults == null ? null : (mappedResults.size() < 1) ? null : mappedResults.get(0));
        BigDecimal result = (singleValueHolder == null ? null : singleValueHolder.getValue());
        return SingleSensorReading
                .builder()
                .deviceId(deviceId)
                .sensorReading(
                        SensorReading
                                .builder()
                                .sensorId(sensorId)
                                .readingType(readingType)
                                .value(result)
                                .build()
                )
                .build();
    }

    /**
     * Verify that the parameters to the public functions satisfy the requirements
     * @param deviceId
     * @param from
     * @param to
     * @param sensorsId
     */
    private void verifyParameters(String deviceId, Date from, Date to, String[] sensorsId) {
        Assert.notNull(deviceId, "[deviceId] is mandatory");
        Assert.notNull(from, "[from] is mandatory");
        Assert.notNull(to, "[to] is mandatory");
        Assert.notEmpty(sensorsId, "Provide at least one sensorId");
        Assert.isTrue(from.before(to), "[from] date must be before [to] date");
    }

    /**
     * Verify that the parameters to the public functions satisfy the requirements
     * @param deviceId
     * @param from
     * @param to
     * @param sensorsId
     */
    private void verifyParameters(String deviceId, Date from, Date to, String sensorsId) {
        Assert.notNull(deviceId, "[deviceId] is mandatory");
        Assert.notNull(from, "[from] is mandatory");
        Assert.notNull(to, "[to] is mandatory");
        Assert.notNull(sensorsId, "[sensorId] is mandatory");
        Assert.isTrue(from.before(to), "[from] date must be before [to] date");
    }
}
