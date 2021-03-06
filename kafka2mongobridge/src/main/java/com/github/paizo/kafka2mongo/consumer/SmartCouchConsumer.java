package com.github.paizo.kafka2mongo.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.paizo.kafka2mongo.model.SmartCouch;
import com.github.paizo.kafka2mongo.repository.SmartCouchRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.lang.String.format;

@Component
@Slf4j
public class SmartCouchConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SmartCouchRepository smartCouchRepository;

    @KafkaListener(groupId = "${kafka.topic.smartCouch.groupId}", topics = "${kafka.topic.smartCouch.raw-topic}")
    public void receive(ConsumerRecord<String, String> consumerRecord) throws IOException {
        log.trace(format("received payload=[%s]", consumerRecord.toString()));
        SmartCouch smartCouch = objectMapper.readValue(consumerRecord.value(), SmartCouch.class);
        smartCouchRepository.save(smartCouch);
    }
}
