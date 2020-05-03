package com.sar.ws.ui.controller;

import com.sar.ws.exceptions.UserServiceException;
import com.sar.ws.service.UserService;
import com.sar.ws.service.VoteService;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.shared.view.UserView;
import com.sar.ws.shared.view.VoteView;
import com.sar.ws.ui.model.request.UserDetailsRequestModel;
import com.sar.ws.ui.model.response.OperationStatusModel;
import com.sar.ws.ui.model.response.RequestOperationName;
import com.sar.ws.ui.model.response.RequestOperationStatus;
import com.sar.ws.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import static com.sar.ws.shared.Roles.ROLE_USER;

@Validated
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
    public UserRest createUser(@RequestBody @Validated(UserDetailsRequestModel.InitialValidation.class) UserDetailsRequestModel userDetails) throws UserServiceException {

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        userDto.setRoles(new HashSet<>(Collections.singletonList(ROLE_USER.name())));

        return userService.create(userDto);
    }

    @PostAuthorize("hasRole('ADMIN') or returnObject.userId == principal.userId")
    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView getUser(@PathVariable String id) {
        return userService.getByUserId(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRest updateUser(@PathVariable String id, @RequestBody @Valid UserDetailsRequestModel userDetails) {

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        return userService.updateUser(id, userDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatusModel deleteUser(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.deleteUser(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserView> getUsers(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {

        if (page > 0) page = page - 1;
        return userService.getUsers(page, limit);
    }

    @PreAuthorize("#userId == principal.userId")
    @PostMapping(path = "/{userId}/votes")
    public OperationStatusModel postVote(@PathVariable String userId,
                                         @RequestParam @Min(1000) long restaurantId) {

        LocalDateTime postTime = LocalDateTime.now();
        return voteService.create(userId, restaurantId, postTime);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or #userId == principal.userId")
    @GetMapping(path = "/{userId}/votes")
    public List<VoteView> getVotes(@Valid @PathVariable String userId,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {

        if (page > 0) page = page - 1;
        return voteService.getVotes(userId, page, limit);
    }
}
