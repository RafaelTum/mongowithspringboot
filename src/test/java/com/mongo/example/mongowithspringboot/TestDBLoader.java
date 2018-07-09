package com.mongo.example.mongowithspringboot;

import com.mongo.example.mongowithspringboot.data.mongo.document.LocationDocument;
import com.mongo.example.mongowithspringboot.data.mongo.document.UserDocument;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

//@Configuration
public class TestDBLoader {

  @Autowired
  private MongoTemplate mongoTemplate;


  @PostConstruct
  public void initDb() {

    LocationDocument location = new LocationDocument();
    LocalDate localDate = LocalDate.of(2017, Month.MAY, 28);
    location.setId("2");
    location.setCountry("Austria");
    location.setDate(localDate);
    mongoTemplate.insert(location);
    List<LocationDocument> locations = new ArrayList<>();
    locations.add(location);
    UserDocument userEntity = new UserDocument("Petros", "Petros", "53", "Talin");
    userEntity.setId("5b2920f36867f655b9d186d7");
    userEntity.setLocations(locations);
    mongoTemplate.insert(userEntity, "userEntity");
  }

  @PreDestroy
  public void dropDb() {
    mongoTemplate.dropCollection("userEntity");
    mongoTemplate.dropCollection("location");

  }
}
