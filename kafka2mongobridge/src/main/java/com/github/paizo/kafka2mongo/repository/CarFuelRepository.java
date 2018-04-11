package com.github.paizo.kafka2mongo.repository;

import com.github.paizo.kafka2mongo.model.CarFuel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarFuelRepository extends MongoRepository<CarFuel, String> {

}
