package com.sar.ws.service.impl;

import com.sar.ws.io.entity.Role;
import com.sar.ws.io.entity.UserEntity;
import com.sar.ws.io.entity.Vote;
import com.sar.ws.shared.Roles;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.shared.dto.VoteDto;
import com.sar.ws.shared.view.UserView;
import com.sar.ws.ui.model.response.UserRest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.*;

import static com.sar.ws.UserTestData.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest extends AbstractServiceTest{



    UserEntity userEntity;
    UserDto userDto;

    Role role = new Role(Roles.ROLE_USER.name());
    Set<Role> roleSet = new HashSet<>();
    List<Vote> voteList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userEntity = getUserEntity();

        userDto = new UserDto();
        userDto.setFirstName(FIRST_NAME);
        userDto.setLastName(LAST_NAME);
        userDto.setPassword(PASSWORD);
        userDto.setEncryptedPassword(ENCRYPTED_PASSWORD);
        userDto.setEmail(EMAIL);
        Set<String> roles = new HashSet<>();
        roles.add(Roles.ROLE_USER.name());
        List<VoteDto> voteDtos = new ArrayList<>();
        userDto.setVotes(voteDtos);
        userDto.setRoles(roles);
    }

    @Test
    void createUser() {
        when(utils.generateUserId(anyInt())).thenReturn(USER_ID);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(ENCRYPTED_PASSWORD);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserRest storedUserDetails = userService.create(userDto);
        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getUserId(), storedUserDetails.getUserId());
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
        assertEquals(userEntity.getEmail(), storedUserDetails.getEmail());
        verify(bCryptPasswordEncoder, times(1)).encode(PASSWORD);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void getUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));
        UserDto userDto = userService.getUser(EMAIL);
        assertNotNull(userDto);
        assertEquals(userEntity.getFirstName(), userDto.getFirstName());
    }

    @Test
    void getUser_UsernameNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        assertThrows(UsernameNotFoundException.class,
                () -> userService.getUser(EMAIL));
    }

    @Test
    void loadUserByUsername() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));
        UserDetails userDetails = userService.loadUserByUsername(EMAIL);
        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(), userEntity.getEmail());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void getByUserId() {
        UserView userViewTest = getUserView();
        when(userRepository.getByUserId(anyString())).thenReturn(Optional.of(userViewTest));
        UserView userView = userService.getByUserId(USER_ID);
        assertNotNull(userView);
        assertEquals(userView.getUserId(), userEntity.getUserId());
        assertEquals(userView.getFirstName(), userEntity.getFirstName());
        assertEquals(userView.getLastName(), userEntity.getLastName());
        assertEquals(userView.getEmail(), userEntity.getEmail());
    }

    @Test
    void updateUser() {
        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(userEntity));
        when(userRepository.save(any())).thenReturn(userEntity);
        UserRest returnValue = userService.updateUser(USER_ID, userDto);
        assertNotNull(returnValue);
        assertEquals(userEntity.getUserId(), returnValue.getUserId());
        assertEquals(userDto.getFirstName(), returnValue.getFirstName());
        assertEquals(userDto.getLastName(), returnValue.getLastName());
        assertEquals(userDto.getEmail(), returnValue.getEmail());
    }

    @Test
    void deleteUser() {
        ArgumentCaptor<UserEntity> arg = ArgumentCaptor.forClass(UserEntity.class);
        userRepository.delete(userEntity);
        verify(userRepository).delete(arg.capture());
        assertEquals(userEntity, arg.getValue());
    }

    @Test
    void getUsers() {
        List<UserView> list = new ArrayList<>(Arrays.asList(getUserView(), getUserView()));

        when(userRepository.getAllBy(any())).thenReturn(list);

        List<UserView> returnValue = userService.getUsers(0, 10);
        assertNotNull(returnValue);
        assertEquals(2, returnValue.size());
        assertEquals(FIRST_NAME, returnValue.get(0).getFirstName());
    }
}