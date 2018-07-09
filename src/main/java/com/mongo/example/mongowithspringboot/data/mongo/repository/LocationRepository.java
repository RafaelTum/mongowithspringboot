package com.mongo.example.mongowithspringboot.data.mongo.repository;

import com.mongo.example.mongowithspringboot.data.mongo.document.LocationDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface LocationRepository extends MongoRepository<LocationDocument,String> {

}
