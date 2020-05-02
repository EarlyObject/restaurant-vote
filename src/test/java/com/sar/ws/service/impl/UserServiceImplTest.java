package com.sar.ws.service.impl;

import com.sar.ws.exceptions.UserServiceException;
import com.sar.ws.io.entity.Role;
import com.sar.ws.io.entity.UserEntity;
import com.sar.ws.io.entity.Vote;
import com.sar.ws.shared.Roles;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.shared.dto.VoteDto;
import com.sar.ws.shared.view.UserView;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
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
        userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUserId(USER_ID);
        userEntity.setFirstName(FIRST_NAME);
        userEntity.setLastName(LAST_NAME);
        userEntity.setEmail(EMAIL);
        userEntity.setEncryptedPassword(ENCRYPTED_PASSWORD);
        userEntity.setVotes(voteList);
        roleSet.add(role);
        userEntity.setRoles(roleSet);

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
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        when(utils.generateUserId(anyInt())).thenReturn(USER_ID);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(ENCRYPTED_PASSWORD);
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
        verify(bCryptPasswordEncoder, times(1)).encode(PASSWORD);
        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    void createUser_UserServiceException() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        assertThrows(UserServiceException.class, () -> userService.createUser(userDto));
    }

    @Test
    void getUser() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        UserDto userDto = userService.getUser(EMAIL);
        assertNotNull(userDto);
        assertEquals(FIRST_NAME, userDto.getFirstName());
    }

    @Test
    void getUser_UsernameNotFoundException() {
        when(userRepository.findByEmail(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class,
                () -> userService.getUser(EMAIL));
    }

    @Test
    void loadUserByUsername() {
        when(userRepository.findByEmail(anyString())).thenReturn(userEntity);
        UserDetails userDetails = userService.loadUserByUsername(EMAIL);
        assertNotNull(userDetails);
        assertEquals(userDetails.getUsername(), userEntity.getEmail());
        assertTrue(userDetails.isEnabled());
    }

    @Test
    void getByUserId() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserView userViewTest = factory.createProjection(UserView.class);
        userViewTest.setUserId(USER_ID);
        userViewTest.setFirstName(FIRST_NAME);
        userViewTest.setLastName(LAST_NAME);
        userViewTest.setEmail(EMAIL);

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
        when(userRepository.findByUserId(anyString())).thenReturn(userEntity);
        when(userRepository.save(any())).thenReturn(userEntity);
        UserDto testUserDto = userService.updateUser(USER_ID, userDto);
        assertNotNull(testUserDto);
        assertEquals(testUserDto.getId(), userEntity.getId());
        assertEquals(testUserDto.getUserId(), userEntity.getUserId());
        assertEquals(testUserDto.getFirstName(), userDto.getFirstName());
        assertEquals(testUserDto.getLastName(), userDto.getLastName());
        assertEquals(testUserDto.getEmail(), userDto.getEmail());
        assertEquals(testUserDto.getVotes(), userDto.getVotes());
        assertEquals(testUserDto.getEncryptedPassword(), userDto.getEncryptedPassword());
        assertEquals(testUserDto.getEmailVerificationStatus(), userDto.getEmailVerificationStatus());
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
        List<UserEntity> list = new ArrayList<>();
        list.add(userEntity);
        list.add(userEntity);
        Page<UserEntity> page = new PageImpl<>(list);
        when(userRepository.findAll(any(Pageable.class))).thenReturn(page);
        List<UserDto> userDtos = userService.getUsers(0, 10);
        assertNotNull(userDtos);
        assertTrue(userDtos.size() == 2);
        assertEquals(FIRST_NAME, userDtos.get(0).getFirstName());
    }
}