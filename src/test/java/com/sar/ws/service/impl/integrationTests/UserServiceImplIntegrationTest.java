package com.sar.ws.service.impl.integrationTests;

import com.sar.ws.exceptions.CustomServiceException;
import com.sar.ws.io.repositories.UserRepository;
import com.sar.ws.service.UserService;
import com.sar.ws.service.impl.UserServiceImpl;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.shared.view.UserView;
import com.sar.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static com.sar.ws.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserService userService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    UserServiceImpl userServiceMock;

    @Mock
    UserRepository userRepositoryMock;

    /*@Test
    void create() {
        UserDto userDto = getUserDto();
        userDto.setLastName("adsa");
        userDto.setFirstName("dfs");
        userDto.setPassword("sdf");
        userDto.setEmail("dfs@sfdf.com");
        userDto.setRoles(new HashSet<>(Collections.singletonList(ROLE_USER.name())));

//        userDto.setEmail("otherEmail@email.com");
        when(userRepository.save(any())).thenReturn(getUserEntity());
        when(userServiceMock.create(any())).thenReturn(getUserRest());
        UserRest userRest = userService.create(userDto);
        assertNotNull(userRest);
        assertEquals(USER_ID, userRest.getUserId());
        assertEquals(FIRST_NAME, userRest.getFirstName());
        assertEquals(LAST_NAME, userRest.getLastName());
        assertEquals("otherEmail@email.com", userRest.getEmail());
    }*/

    @Test
    void getUser() {
        UserDto user = userService.getUser(EMAIL);
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
    void updateUser() {
        UserDto userDto = new UserDto();
        userDto.setFirstName("NewName");
        userDto.setLastName("NewLastName");
        UserRest userRest = userService.updateUser(USER_ID, userDto);
        assertNotNull(userRest);
        assertEquals(USER_ID, userRest.getUserId());
        assertEquals("NewName", userRest.getFirstName());
        assertEquals("NewLastName", userRest.getLastName());
        assertEquals(EMAIL, userRest.getEmail());
    }

    @Test
    void deleteUser() {
        userService.deleteUser(USER_ID);
        assertThrows(CustomServiceException.class, () -> userService.deleteUser(USER_ID));
    }

    @Test
    void deleteUser_wrongUserId() {
        assertThrows(CustomServiceException.class, () -> userService.deleteUser("USER_ID"));
    }

    @Test
    void getUsers() {
        List<UserView> users = userService.getUsers(0, 10);
        assertNotNull(users);
        assertEquals(5, users.size());
    }
}