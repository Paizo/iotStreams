package com.github.paizo.monitorapi.repository;

import com.github.paizo.monitorapi.model.SmartCouch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SmartCouchRepository extends MongoRepository<SmartCouch, String> {

}
