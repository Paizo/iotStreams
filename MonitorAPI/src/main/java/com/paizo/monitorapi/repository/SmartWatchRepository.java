package com.paizo.monitorapi.repository;

import com.paizo.monitorapi.model.Refrigerator;
import com.paizo.monitorapi.model.SmartWatch;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SmartWatchRepository extends MongoRepository<SmartWatch, String> {

}
