package com.mongo.example.mongowithspringboot.service.dto;

import com.mongo.example.mongowithspringboot.data.mongo.document.LocationDocument;
import java.util.List;

public class UserDto {

  private String id;
  private String firstName;
  private String lastName;
  private String age;
  private String city;
  private List<LocationDocument> locations;

  public UserDto() {
  }

  public UserDto(String firstName, String lastName, String age, String city,
      List<LocationDocument> locations) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.city = city;
    this.locations = locations;
  }

  public UserDto(String id, String firstName, String lastName, String age, String city,
      List<LocationDocument> locations) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.age = age;
    this.city = city;
    this.locations = locations;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getAge() {
    return age;
  }

  public void setAge(String age) {
    this.age = age;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public List<LocationDocument> getLocations() {
    return locations;
  }

  public void setLocations(List<LocationDocument> locations) {
    this.locations = locations;
  }
}
