package com.github.paizo.kafka2mongo.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static java.lang.String.format;

@Component
@Slf4j
public class SmartCouchConsumer {

    @KafkaListener(groupId = "${kafka.groupId.smartCouch}", topics = "${kafka.topic.smartCouch}")
    public void receive(ConsumerRecord<?, ?> consumerRecord) {
        log.trace(format("received payload=[%s]", consumerRecord.toString()));
    }
}
