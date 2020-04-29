package com.sar.ws.service.impl;

import com.sar.ws.UserTestData;
import com.sar.ws.exceptions.UserServiceException;
import com.sar.ws.io.entity.Role;
import com.sar.ws.io.entity.UserEntity;
import com.sar.ws.io.entity.Vote;
import com.sar.ws.io.repositories.RoleRepository;
import com.sar.ws.io.repositories.UserRepository;
import com.sar.ws.shared.Roles;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.shared.dto.Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    UserRepository userRepository;

    @Mock
    Utils utils;

    @Mock
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    RoleRepository roleRepository;

    UserEntity userEntity;
    UserDto userDto;

    Role role = new Role(Roles.ROLE_USER.name());
    Set<Role> roleSet = new HashSet<>();
    List<Vote> voteList = new ArrayList<>();


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUserId(UserTestData.USER_ID);
        userEntity.setFirstName(UserTestData.FIRST_NAME);
        userEntity.setLastName(UserTestData.LAST_NAME);
        userEntity.setEmail(UserTestData.EMAIL);
        userEntity.setEncryptedPassword(UserTestData.ENCRYPTED_PASSWORD);
        userEntity.setVotes(voteList);
        roleSet.add(role);
        userEntity.setRoles(roleSet);

        userDto = new UserDto();
        userDto.setFirstName(UserTestData.FIRST_NAME);
        userDto.setLastName(UserTestData.LAST_NAME);
        userDto.setPassword(UserTestData.PASSWORD);
        userDto.setEncryptedPassword(UserTestData.ENCRYPTED_PASSWORD);
        userDto.setEmail(UserTestData.EMAIL);
        Set<String> roles = new HashSet<>();
        roles.add(Roles.ROLE_USER.name());
        userDto.setRoles(roles);
    }

    @Test
    void createUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateUserId(anyInt())).thenReturn(UserTestData.USER_ID);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(UserTestData.ENCRYPTED_PASSWORD);
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        UserDto storedUserDetails = userService.createUser(userDto);
        assertNotNull(storedUserDetails);
        assertEquals(userEntity.getUserId(), storedUserDetails.getUserId());
        assertEquals(userEntity.getFirstName(), storedUserDetails.getFirstName());
        assertEquals(userEntity.getLastName(), storedUserDetails.getLastName());
        assertEquals(userEntity.getEncryptedPassword(), storedUserDetails.getEncryptedPassword());
        assertEquals(userEntity.getEmail(), storedUserDetails.getEmail());
        assertNotNull(storedUserDetails.getVotes());
        assertEquals(0, storedUserDetails.getVotes().size());
        assertEquals(storedUserDetails.getRoles(), roleSet);
        assertEquals(userEntity.getRoles(), storedUserDetails.getRoles());
        verify(bCryptPasswordEncoder, times(1)).encode(UserTestData.PASSWORD);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createUser_UserServiceException() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        assertThrows(UserServiceException.class,
                () -> userService.createUser(userDto));
    }

    @Test
    void getUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        UserDto userDto = userService.getUser(UserTestData.EMAIL);
        assertNotNull(userDto);
        assertEquals(UserTestData.FIRST_NAME, userDto.getFirstName());
    }

    @Test
    void getUser_UsernameNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class,
                () -> userService.getUser(UserTestData.EMAIL));
    }

    /*@Test
    void loadUserByUsername() {
    }

    @Test
    void getByUserId() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void getUsers() {
    }*/
}