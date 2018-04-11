package com.github.paizo.monitorapi.repository;

import com.github.paizo.monitorapi.model.CarFuel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CarFuelRepository extends MongoRepository<CarFuel, String> {

}
