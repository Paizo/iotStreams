package com.github.paizo.monitorapi.repository;

import com.github.paizo.monitorapi.model.SmartWatch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SmartWatchRepository extends MongoRepository<SmartWatch, String> {

}
