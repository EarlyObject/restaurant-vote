package com.sar.ws.ui.controller;

import com.sar.ws.exceptions.UserServiceException;
import com.sar.ws.service.UserService;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.ui.model.request.UserDetailsRequestModel;
import com.sar.ws.ui.model.response.ErrorMessages;
import com.sar.ws.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.awt.*;

@RestController
@RequestMapping("users")//http://localhost:8080/users
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping(path={"/{id}"}, produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserRest getUser(@PathVariable String id) {
        UserRest returnValue = new UserRest();

        UserDto userDto = userService.getUserByUserId(id);
        BeanUtils.copyProperties(userDto, returnValue);

        return returnValue;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE})
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws UserServiceException {
      UserRest returnValue = new UserRest();

      if (userDetails.getFirstName().isEmpty()) {
          throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
      }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

    @PutMapping
    public String updateUser(){
        return "update user was called";
    }

    @DeleteMapping
    public String deleteUser() {
        return "delete user was called";
    }
}
