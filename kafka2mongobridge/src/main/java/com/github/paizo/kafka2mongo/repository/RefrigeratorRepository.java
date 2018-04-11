package com.github.paizo.kafka2mongo.repository;

import com.github.paizo.kafka2mongo.model.Refrigerator;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefrigeratorRepository extends MongoRepository<Refrigerator, String> {

}
