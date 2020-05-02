package com.sar.ws.ui.controller;

import com.sar.ws.exceptions.UserServiceException;
import com.sar.ws.service.impl.UserServiceImpl;
import com.sar.ws.shared.Roles;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.shared.view.UserView;
import com.sar.ws.ui.model.request.UserDetailsRequestModel;
import com.sar.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.*;

import static com.sar.ws.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserDto userDto;

    ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
    UserView userView = factory.createProjection(UserView.class);


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        userView.setUserId(USER_ID);
        userView.setEmail(EMAIL);
        userView.setFirstName(FIRST_NAME);
        userView.setLastName(LAST_NAME);

        userDto = new UserDto();
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME);
        userDto.setEmail(EMAIL);
        userDto.setPassword(PASSWORD);
        userDto.setUserId(USER_ID);
        Set<String> roles = new HashSet<>(Collections.singletonList(Roles.ROLE_USER.name()));
        userDto.setRoles(roles);


        //скорее всего надо будет удалить
       /* userDto.setEncryptedPassword(ENCRYPTED_PASSWORD);
        List<VoteDto> voteDtos = new ArrayList<>();
        userDto.setVotes(voteDtos);*/
    }

    @Test
    void createUser() {
        UserDetailsRequestModel userDetails = new UserDetailsRequestModel();

        //здесь добавить проверку дополнительных полей если нужно
        userDetails.setFirstName(FIRST_NAME);
        when(userService.createUser(any())).thenReturn(userDto);
        UserRest returnValue = userController.createUser(userDetails);
        assertNotNull(returnValue);
        assertEquals(userDto.getUserId(), returnValue.getUserId());
        assertEquals(userDto.getFirstName(), returnValue.getFirstName());
        assertEquals(userDto.getLastName(), returnValue.getLastName());
        assertEquals(userDto.getEmail(), returnValue.getEmail());
    }

    @Test
    void createUser_UserServiceException() {
        UserDetailsRequestModel userDetails = new UserDetailsRequestModel();
        userDetails.setFirstName("");
        assertThrows(UserServiceException.class, () -> userController.createUser(userDetails));
    }

    @Test
    void getUser() {
        when(userService.getByUserId(anyString())).thenReturn(userView);

        UserView returnValue = userController.getUser(USER_ID);
        assertNotNull(returnValue);
        assertEquals(USER_ID, returnValue.getUserId());
        assertEquals(FIRST_NAME, returnValue.getFirstName());
        assertEquals(LAST_NAME, returnValue.getLastName());
        assertEquals(EMAIL, returnValue.getEmail());
    }

    @Test
    void updateUser() {
        UserDetailsRequestModel userDetails = new UserDetailsRequestModel();
        when(userService.updateUser(anyString(), any())).thenReturn(userDto);
        UserRest returnValue = userController.updateUser("", userDetails);
        assertNotNull(returnValue);
        assertEquals(userDto.getUserId(), returnValue.getUserId());
        assertEquals(userDto.getFirstName(), returnValue.getFirstName());
        assertEquals(userDto.getLastName(), returnValue.getLastName());
        assertEquals(userDto.getEmail(), returnValue.getEmail());
    }

    @Test
    void getUsers() {
        List<UserDto> userDtos = new ArrayList<>();
        userDtos.add(userDto);
        userDtos.add(userDto);
        when(userService.getUsers(anyInt(), anyInt())).thenReturn(userDtos);
        List<UserRest> users = userController.getUsers(1, 10);
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals(userDto.getFirstName(), users.get(0).getFirstName());
    }

  /*  @Test
    void postVote() {
    }

    @Test
    void getVotes() {
    }*/
}