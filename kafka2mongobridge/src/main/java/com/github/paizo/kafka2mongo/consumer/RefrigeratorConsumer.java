package com.github.paizo.kafka2mongo.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.paizo.kafka2mongo.model.Refrigerator;
import com.github.paizo.kafka2mongo.repository.RefrigeratorRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.lang.String.format;

@Component
@Slf4j
public class RefrigeratorConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RefrigeratorRepository refrigeratorRepository;

    @KafkaListener(groupId = "${kafka.topic.refrigerator.groupId}", topics = "${kafka.topic.refrigerator.raw-topic}")
    public void receive(ConsumerRecord<String, String> consumerRecord) throws IOException {
        log.trace(format("received payload=[%s]", consumerRecord.toString()));
        Refrigerator refrigerator = objectMapper.readValue(consumerRecord.value(), Refrigerator.class);
        refrigeratorRepository.save(refrigerator);
    }
}
