package com.github.paizo.producer.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.paizo.producer.TestUtils;
import com.github.paizo.producer.model.CarFuel;
import com.github.paizo.producer.model.Refrigerator;
import com.github.paizo.producer.model.SmartCouch;
import com.github.paizo.producer.model.SmartWatch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.lang.String.format;


@Component
@Slf4j
public class ScheduledTasks {

    @Autowired
    private TestUtils testUtils;

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Value("${paizo.topic.smartCouch:smart-couch-raw}")
    private String smartCouchTopic;

    @Value("${paizo.topic.smartWatch:smart-watch-raw}")
    private String smartWatchTopic;

    @Value("${paizo.topic.carFuel:car-fuel-raw}")
    private String carFuelTopic;

    @Value("${paizo.topic.refrigerator:refrigerator-raw}")
    private String refrigeratorTopic;


    @Scheduled(fixedDelayString  = "${paizo.iot-messages-rate.smartCouch:1000}")
    public void sendSmartCouchReading2Topic() throws JsonProcessingException {
        SmartCouch smartCouch = testUtils.generateSmartCouchReadings("smart-couch");
        kafkaTemplate.send(smartCouchTopic, objectMapper.writeValueAsString(smartCouch));
        log.trace(format("sent [%s]", smartCouch));
    }

    @Scheduled(fixedDelayString  = "${paizo.iot-messages-rate.smartWatch:1000}")
    public void sendSmartWatchReading2Topic() throws JsonProcessingException {
        SmartWatch smartWatch = testUtils.generateSmartWatchReadings("smart-watch");
        kafkaTemplate.send(smartWatchTopic, objectMapper.writeValueAsString(smartWatch));
        log.trace(format("sent [%s]", smartWatch));
    }

    @Scheduled(fixedDelayString  = "${paizo.iot-messages-rate.carFuel:1000}")
    public void sendCarFuelReading2Topic() throws JsonProcessingException {
        CarFuel carFuel = testUtils.generateCarFuelReadings("car-fuel");
        kafkaTemplate.send(carFuelTopic, objectMapper.writeValueAsString(carFuel));
        log.trace(format("sent [%s]", carFuel));
    }

    @Scheduled(fixedDelayString  = "${paizo.iot-messages-rate.refrigerator:1000}")
    public void sendRefrigeratorReading2Topic() throws JsonProcessingException {
        Refrigerator refrigerator = testUtils.generateRefrigeratorReadings("refrigerator");
        kafkaTemplate.send(refrigeratorTopic, objectMapper.writeValueAsString(refrigerator));
        log.trace(format("sent [%s]", refrigerator));
    }
}