package com.mongo.example.mongowithspringboot.service;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.mongo.example.mongowithspringboot.data.mongo.document.LocationDocument;
import com.mongo.example.mongowithspringboot.data.mongo.document.UserDocument;
import com.mongo.example.mongowithspringboot.data.mongo.repository.UserRepository;
import com.mongo.example.mongowithspringboot.service.dto.UserDto;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  UserService userService = new UserServiceImpl(userRepository);

  @Test
  public void getAllUsers() {
    LocationDocument location = new LocationDocument();
    location.setCountry("Austria");
    location.setDate(LocalDate.of(2019, 1,1));
    UserDocument userEntity = new UserDocument("Rafael","Tumasyan",
        "29","Yerevan",Arrays.asList(location));
    List<UserDocument> testUsers = new ArrayList<>();
    testUsers.add(userEntity);
    when(userRepository.findAll()).thenReturn(testUsers);
    List<UserDto> mockUserDtoList = userService.getAllUsers();

    verify(userRepository).findAll();

    Assert.assertEquals(mockUserDtoList.size(),1);
  }

  @Test
  public void getUsersByLocationAndDate() {
    LocalDate localDate= LocalDate.now();
    LocationDocument location = new LocationDocument();
    location.setCountry("Germany");
    location.setDate(localDate);
    UserDocument userEntity = new UserDocument("Poxos","poxosyan",
        "58","Yerevan",Arrays.asList(location));
    List<UserDocument> testUsers = new ArrayList<>();
    testUsers.add(userEntity);
    when(userRepository.findUserEntitiesByLocationsCountry_CountryAndLocationsDate_Date
        ("Germany",localDate)).thenReturn(testUsers);

    List<UserDto> mockUserDtoList = userService.getUsersByLocationAndDate("Germany",localDate);

    verify(userRepository).findUserEntitiesByLocationsCountry_CountryAndLocationsDate_Date("Germany",localDate);

    Assert.assertEquals(mockUserDtoList.size(),1);
  }

  @Test
  public void getUsersByLocation() {
    LocalDate localDate= LocalDate.now();
    LocationDocument location = new LocationDocument();
    location.setCountry("Germany");
    location.setDate(localDate);
    UserDocument userEntity = new UserDocument("Poxos","poxosyan",
        "58","Yerevan",Arrays.asList(location));
    List<UserDocument> testUsers = new ArrayList<>();
    testUsers.add(userEntity);
    when(userRepository.findUserEntitiesByLocationsCountry_Country("Germany")).thenReturn(testUsers);

    List<UserDto> mockUserDtoList = userService.getUsersByLocation("Germany");

    verify(userRepository).findUserEntitiesByLocationsCountry_Country("Germany");

    Assert.assertEquals(mockUserDtoList.size(),1);
  }

  @Test
  public void addUser() {
  }

  @Test
  public void deleteUserById() {
  }

  @Test
  public void updateUser() {
  }

  @Test
  public void findUserById() {
    String id = "1";
    LocalDate localDate= LocalDate.now();
    LocationDocument location = new LocationDocument();
    location.setCountry("Germany");
    location.setDate(localDate);
    UserDocument userEntity = new UserDocument("Poxos","poxosyan",
        "58","Yerevan",Arrays.asList(location));
    userEntity.setId(id);
    when(userRepository.findUserEntityById(id)).thenReturn(userEntity);
    UserDto userDto = userService.findUserById(id);
    verify(userRepository).findUserEntityById(id);
    Assert.assertEquals(userDto.getAge(),"58");
  }
}