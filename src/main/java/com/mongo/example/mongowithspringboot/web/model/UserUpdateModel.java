package com.mongo.example.mongowithspringboot.web.model;

public class UserUpdateModel extends UserCreateModel{

  private String id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }
}
