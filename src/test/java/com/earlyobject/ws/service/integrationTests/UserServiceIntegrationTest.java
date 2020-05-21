package com.earlyobject.ws.service.integrationTests;

import com.earlyobject.ws.exceptions.NotFoundException;
import com.earlyobject.ws.io.repositories.UserRepository;
import com.earlyobject.ws.service.UserService;
import com.earlyobject.ws.shared.dto.UserDto;
import com.earlyobject.ws.shared.view.UserView;
import com.earlyobject.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static com.earlyobject.ws.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Test
    void get() {
        UserDto user = userService.get(EMAIL);
        assertNotNull(user);
        assertEquals(USER_ID, user.getUserId());
        assertEquals(FIRST_NAME, user.getFirstName());
        assertEquals(LAST_NAME, user.getLastName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(ENCRYPTED_PASSWORD, user.getEncryptedPassword());
        assertTrue(user.getEmailVerificationStatus());
    }

    @Test
    void loadUserByUsername() {
        UserDetails userDetails = userService.loadUserByUsername(EMAIL);
        assertNotNull(userDetails);
        assertEquals(EMAIL, userDetails.getUsername());
        assertEquals(ENCRYPTED_PASSWORD, userDetails.getPassword());
    }

    @Test
    void getByUserId() {
        UserView userView = userService.getByUserId(USER_ID);
        assertNotNull(userView);
        assertEquals(USER_ID, userView.getUserId());
        assertEquals(FIRST_NAME, userView.getFirstName());
        assertEquals(LAST_NAME, userView.getLastName());
        assertEquals(EMAIL, userView.getEmail());
    }

    @Test
    void update() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("NewName");
        userDto.setLastName("NewLastName");
        UserRest userRest = userService.update(USER_ID, userDto);
        assertNotNull(userRest);
        assertEquals(USER_ID, userRest.getUserId());
        assertEquals("NewName", userRest.getFirstName());
        assertEquals("NewLastName", userRest.getLastName());
        assertEquals(EMAIL, userRest.getEmail());
    }

    @Test
    void delete() {
        userService.delete(USER_ID);
        assertThrows(NotFoundException.class, () -> userService.delete(USER_ID));
    }

    @Test
    void delete_wrongUserId() {
        assertThrows(NotFoundException.class, () -> userService.delete("USER_ID"));
    }

    @Test
    void getAll() {
        List<UserView> users = userService.getAll(0, 10);
        assertNotNull(users);
        assertEquals(5, users.size());
    }
}