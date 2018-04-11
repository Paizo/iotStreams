package com.github.paizo.kafka2mongo.repository;


import com.github.paizo.kafka2mongo.model.SmartWatch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SmartWatchRepository extends MongoRepository<SmartWatch, String> {

}
