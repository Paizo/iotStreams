package com.paizo.monitorapi.repository;

import com.paizo.monitorapi.model.SmartCouch;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface SmartCouchRepository extends MongoRepository<SmartCouch, String>, QuerydslPredicateExecutor<SmartCouch> {

}
