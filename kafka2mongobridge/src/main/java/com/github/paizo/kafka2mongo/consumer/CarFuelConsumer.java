package com.github.paizo.kafka2mongo.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.paizo.kafka2mongo.model.CarFuel;
import com.github.paizo.kafka2mongo.repository.CarFuelRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static java.lang.String.format;

@Component
@Slf4j
public class CarFuelConsumer {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CarFuelRepository carFuelRepository;

    @KafkaListener(groupId = "${kafka.topic.carFuel.groupId}", topics = "${kafka.topic.carFuel.raw-topic}")
    public void receive(ConsumerRecord<String, String> consumerRecord) throws IOException {
        log.trace(format("received payload=[%s]", consumerRecord.toString()));
        CarFuel carFuel = objectMapper.readValue(consumerRecord.value(), CarFuel.class);
        carFuelRepository.save(carFuel);
    }
}
