package com.paizo.monitorapi.repository;

import com.paizo.monitorapi.model.Refrigerator;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RefrigeratorRepository extends MongoRepository<Refrigerator, String> {

}
