package com.sar.ws.ui.controller;

import com.sar.ws.exceptions.UserServiceException;
import com.sar.ws.io.entity.Vote;
import com.sar.ws.io.repositories.RestaurantRepository;
import com.sar.ws.io.repositories.UserRepository;
import com.sar.ws.io.repositories.VoteRepository;
import com.sar.ws.service.UserService;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.shared.view.MealView;
import com.sar.ws.shared.view.UserView;
import com.sar.ws.shared.view.VoteView;
import com.sar.ws.ui.model.request.UserDetailsRequestModel;
import com.sar.ws.ui.model.response.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static com.sar.ws.shared.Roles.ROLE_USER;

@RestController
@RequestMapping("users") //http://localhost:8080/restaurant-vote/users
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RestaurantRepository restaurantRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VoteRepository voteRepository;

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) throws UserServiceException {
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
    public UserView getUser(@PathVariable String id) {
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
    public OperationStatusModel deleteUser(@PathVariable String id) {
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

    @PostMapping(path = "/{userId}/votes")
    public OperationStatusModel postVote(@PathVariable String userId, @RequestParam long restaurantId) {
        LocalDateTime postTime = LocalDateTime.now();
//        LocalDateTime postTime = LocalDateTime.of(2020, 4, 26, 10,15);
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.VOTE.name());

        long id = userRepository.findByUserId(userId).getId();
        Vote voteOfDay = voteRepository.findByUserIdAndDate(id, postTime.toLocalDate());
        if (voteOfDay == null) {
            Vote vote = new Vote(id, restaurantId, postTime);
            voteRepository.save(vote);
        } else {
            if (postTime.toLocalTime().isAfter(LocalTime.of(11, 00, 00))) {
                returnValue.setOperationResult(RequestOperationStatus.DENIED.name());
                return returnValue;
            } else {
                voteOfDay.setCreated(postTime);
                voteOfDay.setRestaurantId(restaurantId);
                voteRepository.save(voteOfDay);
            }
        }
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @GetMapping(path = "/{userId}/votes")
    public List<VoteView> getVotes(@PathVariable String userId,
                                   @RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {

        if (page > 0) page = page - 1;

        long id = userRepository.findByUserId(userId).getId();

        Pageable pageable = PageRequest.of(page, limit);
        return voteRepository.getAllByUserId(id, pageable);
    }
}
