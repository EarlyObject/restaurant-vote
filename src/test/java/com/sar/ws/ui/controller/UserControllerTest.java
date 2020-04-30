package com.sar.ws.ui.controller;

import com.sar.ws.service.impl.UserServiceImpl;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.shared.view.UserView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import static com.sar.ws.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
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

     /*   userDto = new UserDto();
        userDto.setUserId(USER_ID);
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME);
        userDto.setPassword(PASSWORD);
        userDto.setEncryptedPassword(ENCRYPTED_PASSWORD);
        userDto.setEmail(EMAIL);
        Set<String> roles = new HashSet<>();
        roles.add(Roles.ROLE_USER.name());
        userDto.setRoles(roles);
        List<VoteDto> voteDtos = new ArrayList<>();
        userDto.setVotes(voteDtos);*/
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
}