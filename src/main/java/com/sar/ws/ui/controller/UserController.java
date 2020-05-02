package com.sar.ws.ui.controller;

import com.sar.ws.exceptions.UserServiceException;
import com.sar.ws.service.UserService;
import com.sar.ws.service.VoteService;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.shared.view.UserView;
import com.sar.ws.shared.view.VoteView;
import com.sar.ws.ui.model.request.UserDetailsRequestModel;
import com.sar.ws.ui.model.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static com.sar.ws.shared.Roles.ROLE_USER;

@RestController
@RequestMapping("users") //http://localhost:8080/restaurant-vote/users
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    VoteService voteService;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRest createUser(@Valid @RequestBody UserDetailsRequestModel userDetails) throws UserServiceException {
        UserRest returnValue = new UserRest();

        if (userDetails.getFirstName().isEmpty()) {
            throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());
        }

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        userDto.setRoles(new HashSet<>(Arrays.asList(ROLE_USER.name())));

        UserDto createdUser = userService.createUser(userDto);
        BeanUtils.copyProperties(createdUser, returnValue);

        return returnValue;
    }

    @PostAuthorize("hasRole('ADMIN') or returnObject.userId == principal.userId")
    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView getUser(@Valid @PathVariable String id) {
        return userService.getByUserId(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {
        UserRest returnValue = new UserRest();

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);

        UserDto updatedUser = userService.updateUser(id, userDto);
        BeanUtils.copyProperties(updatedUser, returnValue);

        return returnValue;
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatusModel deleteUser(@Valid @PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());

        return returnValue;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserRest> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {
        List<UserRest> returnValue = new ArrayList<>();

        if (page > 0) page = page - 1;

        List<UserDto> users = userService.getUsers(page, limit);
        for (UserDto userDto : users) {
            UserRest userModel = new UserRest();
            BeanUtils.copyProperties(userDto, userModel);
            returnValue.add(userModel);
        }

        return returnValue;
    }

    @PreAuthorize("#userId == principal.userId")
    @PostMapping(path = "/{userId}/votes")
    public OperationStatusModel postVote(@Valid @PathVariable String userId, @Valid @RequestParam long restaurantId) {
        LocalDateTime postTime = LocalDateTime.now();

        return voteService.create(userId, restaurantId, postTime);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.userId")
    @GetMapping(path = "/{userId}/votes")
    public List<VoteView> getVotes(@Valid @PathVariable String userId,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {

        if (page > 0) page = page - 1;
        Pageable pageable = PageRequest.of(page, limit);

        return voteService.getVotes(userId, pageable);
    }
}
