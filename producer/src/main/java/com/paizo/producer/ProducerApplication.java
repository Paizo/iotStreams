package com.paizo.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;


@SpringBootApplication
@EnableScheduling
public class ProducerApplication {

//    @Autowired
//    private KafkaTemplate<String, String> template;


    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

}