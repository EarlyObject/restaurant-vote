package com.earlyobject.ws.ui.controller;

import com.earlyobject.ws.exceptions.CustomServiceException;
import com.earlyobject.ws.service.UserService;
import com.earlyobject.ws.service.VoteService;
import com.earlyobject.ws.shared.Roles;
import com.earlyobject.ws.shared.dto.UserDto;
import com.earlyobject.ws.shared.utils.AuthUtil;
import com.earlyobject.ws.shared.view.UserView;
import com.earlyobject.ws.shared.view.VoteView;
import com.earlyobject.ws.ui.model.request.UserDetailsRequestModel;
import com.earlyobject.ws.ui.model.response.OperationStatusModel;
import com.earlyobject.ws.ui.model.response.RequestOperationName;
import com.earlyobject.ws.ui.model.response.RequestOperationStatus;
import com.earlyobject.ws.ui.model.response.UserRest;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Validated
@RestController
@RequestMapping("users")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    VoteService voteService;

    @ApiOperation(value = "Create User Web Service End Point",
            notes = "${userController.create.notes}")
    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRest createUser(@RequestBody @Validated(UserDetailsRequestModel.InitialValidation.class) UserDetailsRequestModel userDetails) throws CustomServiceException {

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        userDto.setRoles(new HashSet<>(Collections.singletonList(Roles.ROLE_USER.name())));

        return userService.create(userDto);
    }

    @ApiOperation(value = "Get User Web Service End Point", notes = "${userController.get.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}",  paramType = "header")})
    @PostAuthorize("hasRole('ADMIN') or returnObject.userId == principal.userId")
    @GetMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserView get(@PathVariable String id) {
        return userService.getByUserId(id);
    }


    @ApiOperation(value = "Update User Web Service End Point", notes = "${userController.update.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}",  paramType = "header")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @PutMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRest update(@PathVariable String id, @RequestBody @Valid UserDetailsRequestModel userDetails) {

        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(userDetails, userDto);
        return userService.update(id, userDto);
    }


    @ApiOperation(value = "Update User to Admin Web Service End Point", notes = "${userController.updateToAdmin.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}",  paramType = "header")})
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path = {"/{id}/admin"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public UserRest updateToAdmin(@PathVariable String id) {

        UserDto userDto = new UserDto();
        List<String> roles = Arrays.asList(Roles.ROLE_USER.name(), Roles.ROLE_ADMIN.name());
        userDto.setRoles(new HashSet<>(roles));

        return userService.updateToAdmin(id, userDto);
    }

    @ApiOperation(value = "Delete User Web Service End Point", notes = "${userController.delete.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}",  paramType = "header")})
    @PreAuthorize("hasRole('ROLE_ADMIN') or #id == principal.userId")
    @DeleteMapping(path = {"/{id}"}, produces = MediaType.APPLICATION_JSON_VALUE)
    public OperationStatusModel delete(@PathVariable String id) {
        OperationStatusModel returnValue = new OperationStatusModel();
        returnValue.setOperationName(RequestOperationName.DELETE.name());

        userService.delete(id);
        returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
        return returnValue;
    }

    @ApiOperation(value = "Get All Users Web Service End Point", notes = "${userController.getAll.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}",  paramType = "header")})
    @Secured("ROLE_ADMIN")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<UserView> getAll(@RequestParam(value = "page", defaultValue = "0") int page,
                                 @RequestParam(value = "limit", defaultValue = "25") int limit) {

        return userService.getAll(page, limit);
    }

    @ApiOperation(value = "Post the Vote of the User Web Service End Point", notes = "${userController" +
            ".postVote.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}",  paramType = "header")})
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PostMapping(path = "/votes")
    public OperationStatusModel postVote(@RequestParam long restaurantId) {

        long id = AuthUtil.getId();

        LocalDateTime postTime = LocalDateTime.now();
        return voteService.create(id, restaurantId, postTime);
    }

    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @PutMapping(path = "/votes")
    public OperationStatusModel updateVote(@RequestParam long restaurantId) {

        long id = AuthUtil.getId();

        LocalDateTime postTime = LocalDateTime.now();
        return voteService.update(id, restaurantId, postTime);
    }



    @ApiOperation(value = "Get All Votes of the User Web Service End Point", notes = "${userController.getVotes.notes}")
    @ApiImplicitParams({@ApiImplicitParam(name = "authorization", value = "${userController.authorizationHeader.description}",  paramType = "header")})
    @Secured({"ROLE_USER","ROLE_ADMIN"})
    @GetMapping(path = "/votes")
    public List<VoteView> getVotes(@RequestParam(value = "page", defaultValue = "0") int page,
                                   @RequestParam(value = "limit", defaultValue = "25") int limit) {
        long id = AuthUtil.getId();
        return voteService.getAll(id, page, limit);
    }
}
