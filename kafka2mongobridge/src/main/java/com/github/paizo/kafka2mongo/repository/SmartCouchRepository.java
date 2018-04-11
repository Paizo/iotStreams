package com.github.paizo.kafka2mongo.repository;

import com.github.paizo.kafka2mongo.model.SmartCouch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SmartCouchRepository extends MongoRepository<SmartCouch, String> {

}
