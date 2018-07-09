package com.mongo.example.mongowithspringboot.web.api;

import com.mongo.example.mongowithspringboot.service.UserService;
import com.mongo.example.mongowithspringboot.service.dto.UserDto;
import com.mongo.example.mongowithspringboot.web.model.UserCreateModel;
import com.mongo.example.mongowithspringboot.web.model.UserModel;
import com.mongo.example.mongowithspringboot.web.model.UserUpdateModel;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  private UserService userService;

  public UserController(UserService userService) {
    this.userService = userService;
  }

  /**
   *will show all users

   */
  @GetMapping
  public ResponseEntity getAllUsers() {
    List<UserDto> allUsers = userService.getAllUsers();
      return new ResponseEntity(allUsers, HttpStatus.OK);
  }

  @PostMapping
  public UserModel addUser(@RequestBody UserCreateModel model) {
    UserDto userDto = new UserDto();
    userDto.setId("");
    userDto.setAge(model.getAge());
    userDto.setCity(model.getCity());
    userDto.setFirstName(model.getFirstName());
    userDto.setLastName(model.getLastName());
    userDto.setLocations(model.getLocations());
    String userId = userService.addUser(userDto);

    UserDto userById = userService.findUserById(userId);
    UserModel userModel = new UserModel();
    userModel.setId(userById.getId());
    userModel.setAge(userById.getAge());
    userModel.setFirstName(userById.getFirstName());
    userModel.setLastName(userById.getLastName());
    userModel.setLocations(userById.getLocations());
    return userModel;
  }

  @PutMapping
  public ResponseEntity updateUser(@RequestBody UserUpdateModel model) {
    UserDto userDto = new UserDto();
    userDto.setId(model.getId());
    userDto.setAge(model.getAge());
    userDto.setCity(model.getCity());
    userDto.setFirstName(model.getFirstName());
    userDto.setLastName(model.getLastName());
    userService.updateUser(userDto);
    return new ResponseEntity(HttpStatus.OK);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity deleteUserById(@PathVariable String id) {
    userService.deleteUserById(id);
    return new ResponseEntity(HttpStatus.OK);
  }

  @GetMapping(value = "/{location}")
  public ResponseEntity findUsersByLocation(@PathVariable String location) {
    List<UserDto> usersByLocations = userService.getUsersByLocation(location);
      return new ResponseEntity(usersByLocations, HttpStatus.OK);
  }

  @GetMapping(value = "/{location}/{date}")
  public ResponseEntity findUsersByLocation(@PathVariable String location,
      @PathVariable String date) {
    LocalDate localDate = LocalDate.parse(date);
    List<UserDto> usersByLocationAndDate = userService
        .getUsersByLocationAndDate(location, localDate);
    return new ResponseEntity(usersByLocationAndDate, HttpStatus.OK);
  }

}
