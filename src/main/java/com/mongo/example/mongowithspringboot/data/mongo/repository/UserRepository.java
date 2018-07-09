package com.mongo.example.mongowithspringboot.data.mongo.repository;

import com.mongo.example.mongowithspringboot.data.mongo.document.UserDocument;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserDocument, String> {
  List<UserDocument> findUserEntitiesByLocationsCountry_Country(String country);
  List<UserDocument> findUserEntitiesByLocationsCountry_CountryAndLocationsDate_Date(String country, LocalDate date);
  List<UserDocument> findUserEntitiesByLocationsDate_Date(LocalDate date);
  UserDocument findUserEntityById(String id);


}
