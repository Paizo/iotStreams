package com.paizo.monitorapi.repository;

import com.paizo.monitorapi.model.SmartCouch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SmartCouchRepository extends MongoRepository<SmartCouch, String> {

}
