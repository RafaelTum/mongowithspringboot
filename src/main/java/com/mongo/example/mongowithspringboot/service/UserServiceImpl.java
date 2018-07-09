package com.mongo.example.mongowithspringboot.service;

import com.mongo.example.mongowithspringboot.data.mongo.document.UserDocument;
import com.mongo.example.mongowithspringboot.data.mongo.repository.UserRepository;
import com.mongo.example.mongowithspringboot.service.dto.UserDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;


  @Autowired
  public UserServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Transactional
  @Override
  public List<UserDto> getAllUsers() {
    List<UserDto> userDtoList = userRepository.findAll().
        stream()
        .map(userDocument -> new UserDto(userDocument.getId(),userDocument.getFirstName(),
                                          userDocument.getLastName(),userDocument.getAge(),
                                          userDocument.getCity(),userDocument.getLocations()))
        .collect(Collectors.toList());
    return userDtoList;
  }

  @Transactional
  @Override
  public List<UserDto> getUsersByLocationAndDate(String country, LocalDate localDate) {
    List<UserDocument> userEntities = userRepository
        .findUserEntitiesByLocationsCountry_CountryAndLocationsDate_Date(country, localDate);
    List<UserDto> userDtoList = new ArrayList<>();
    if (userEntities.size() > 0) {
      for (UserDocument userEntity : userEntities) {
        UserDto userDto = new UserDto(userEntity.getId(), userEntity.getFirstName(),
            userEntity.getLastName(), userEntity.getAge(), userEntity.getCity(),
            userEntity.getLocations());
        userDtoList.add(userDto);
      }
    }
    return userDtoList;
  }

  @Transactional
  @Override
  public List<UserDto> getUsersByLocation(String country) {
    List<UserDocument> userEntities = userRepository
        .findUserEntitiesByLocationsCountry_Country(country);
    List<UserDto> userDtoList = new ArrayList<>();
    if (userEntities.size() > 0) {
      for (UserDocument userEntity : userEntities) {
        UserDto userDto = new UserDto(userEntity.getId(), userEntity.getFirstName(),
            userEntity.getLastName(), userEntity.getAge(), userEntity.getCity(),
            userEntity.getLocations());
        userDtoList.add(userDto);
      }
    }
    return userDtoList;
  }

  @Transactional
  @Override
  public String addUser(UserDto userDto) {
    UserDocument userDocument = new UserDocument(userDto.getFirstName(), userDto.getLastName(),
        userDto.getAge(),
        userDto.getCity(),
        userDto.getLocations());

    UserDocument savedUser = userRepository.save(userDocument);

    return savedUser.getId();
  }

  @Transactional
  @Override
  public void deleteUserById(String id) {
    userRepository.deleteById(id);
  }

  @Transactional
  @Override
  public void updateUser(UserDto userDto) {
    UserDocument userEntity = userRepository.findById(userDto.getId())
        .orElseThrow(() -> new RuntimeException("No user with such id"));
    UserDocument userEntityUpdated = new UserDocument(userEntity.getId(), userDto.getFirstName(),
          userDto.getLastName(),
          userDto.getAge(),
          userDto.getCity(), userDto.getLocations());
    userRepository.save(userEntityUpdated);
  }

  @Override
  public UserDto findUserById(String id) {
    UserDto userDto = null;
    UserDocument userEntity = userRepository.findUserEntityById(id);
    if (userEntity != null) {
      userDto = new UserDto(userEntity.getId(), userEntity.getFirstName(),
          userEntity.getLastName(), userEntity.getAge(), userEntity.getCity(),
          userEntity.getLocations());
    }
    return userDto;
  }
}
