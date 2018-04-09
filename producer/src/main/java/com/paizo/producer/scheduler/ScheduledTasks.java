package com.paizo.producer.scheduler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paizo.producer.TestUtils;
import com.paizo.producer.model.SmartCouch;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    private ObjectMapper objectMapper = new ObjectMapper();

    @Scheduled(fixedRate = 1000)
    public void sendCouchReading2Topic() throws JsonProcessingException {
        SmartCouch smartCouch = testUtils.generateSmartCouchReadings("smart-couch");
        kafkaTemplate.send("smart-couch", objectMapper.writeValueAsString(smartCouch));
        log.trace(format("sent [%s]", smartCouch));
    }
}