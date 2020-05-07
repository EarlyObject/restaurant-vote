package com.earlyobject.ws.ui.controller;

import com.earlyobject.ws.service.impl.UserServiceImpl;
import com.earlyobject.ws.shared.view.UserView;
import com.earlyobject.ws.ui.model.request.UserDetailsRequestModel;
import com.earlyobject.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.earlyobject.ws.UserTestData.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @InjectMocks
    UserController userController;

    @Mock
    UserServiceImpl userService;

    UserRest userRest;
    UserView userView = getUserView();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
       userRest = getUserRest();
    }

    @Test
    void createUser() {
        UserDetailsRequestModel userDetails = new UserDetailsRequestModel();
        userDetails.setFirstName(FIRST_NAME);
        when(userService.create(any())).thenReturn(userRest);
        UserRest returnValue = userController.createUser(userDetails);
        assertNotNull(returnValue);
        assertEquals(USER_ID, returnValue.getUserId());
        assertEquals(FIRST_NAME, returnValue.getFirstName());
        assertEquals(LAST_NAME, returnValue.getLastName());
        assertEquals(EMAIL, returnValue.getEmail());
    }

    @Test
    void getUser() {
        when(userService.getByUserId(anyString())).thenReturn(userView);
        UserView returnValue = userController.get(USER_ID);
        assertNotNull(returnValue);
        assertEquals(USER_ID, returnValue.getUserId());
        assertEquals(FIRST_NAME, returnValue.getFirstName());
        assertEquals(LAST_NAME, returnValue.getLastName());
        assertEquals(EMAIL, returnValue.getEmail());
    }

    @Test
    void updateUser() {
        UserDetailsRequestModel userDetails = new UserDetailsRequestModel();
        when(userService.update(anyString(), any())).thenReturn(userRest);
        UserRest returnValue = userController.update("", userDetails);
        assertNotNull(returnValue);
        assertEquals(USER_ID, returnValue.getUserId());
        assertEquals(FIRST_NAME, returnValue.getFirstName());
        assertEquals(LAST_NAME, returnValue.getLastName());
        assertEquals(EMAIL, returnValue.getEmail());
    }

    @Test
    void getUsers() {
        List<UserView> userViews = new ArrayList<>(Arrays.asList(getUserView(), getUserView()));
        when(userService.getAll(anyInt(), anyInt())).thenReturn(userViews);
        List<UserView> returnValue = userController.getAll(1, 10);
        assertNotNull(returnValue);
        assertEquals(2, returnValue.size());
        assertEquals(FIRST_NAME, returnValue.get(0).getFirstName());
    }
}