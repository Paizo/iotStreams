package com.github.paizo.kafka2mongo.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.paizo.kafka2mongo.model.SmartWatch;
import com.github.paizo.kafka2mongo.repository.SmartWatchRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.lang.String.format;

@Component
@Slf4j
public class SmartWatchConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SmartWatchRepository smartWatchRepository;

    @KafkaListener(groupId = "${kafka.topic.smartWatch.groupId}", topics = "${kafka.topic.smartWatch.raw-topic}")
    public void receive(ConsumerRecord<String, String> consumerRecord) throws IOException {
        log.trace(format("received payload=[%s]", consumerRecord.toString()));
        SmartWatch smartWatch = objectMapper.readValue(consumerRecord.value(), SmartWatch.class);
        smartWatchRepository.save(smartWatch);
    }
}
