package com.mongo.example.mongowithspringboot.service;

import com.mongo.example.mongowithspringboot.service.dto.UserDto;
import java.time.LocalDate;
import java.util.List;

public interface UserService {

  List<UserDto> getAllUsers();

  List<UserDto> getUsersByLocation(String country);
  List<UserDto> getUsersByLocationAndDate(String country, LocalDate localDate);
  String addUser(UserDto userDto);
  void deleteUserById(String id);
  void updateUser(UserDto userDto);
  UserDto findUserById(String id);
}
