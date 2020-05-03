package com.sar.ws.service.impl;

import com.sar.ws.exceptions.UserServiceException;
import com.sar.ws.io.entity.Role;
import com.sar.ws.io.entity.UserEntity;
import com.sar.ws.io.repositories.RoleRepository;
import com.sar.ws.io.repositories.UserRepository;
import com.sar.ws.security.UserPrincipal;
import com.sar.ws.service.UserService;
import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.shared.dto.Utils;
import com.sar.ws.shared.view.UserView;
import com.sar.ws.ui.model.response.ErrorMessages;
import com.sar.ws.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public UserRest create(UserDto userDto) {

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        userEntity.setUserId(utils.generateUserId(20));
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        Collection<Role> roleEntities = new HashSet<>();
        for (String role : userDto.getRoles()) {
            Role roleEntity = roleRepository.findByName(role);
            if (roleEntity != null) {
                roleEntities.add(roleEntity);
            }
        }
        userEntity.setRoles(roleEntities);

        UserEntity savedUser = userRepository.save(userEntity);
        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(savedUser, returnValue);
        return returnValue;
    }

    @Override
    public UserDto getUser(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity.get(), returnValue);
        return returnValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }
        return new UserPrincipal(userEntity.get());
    }

    @Override
    public UserView getByUserId(String userId) {
        Optional<UserView> userViewOptional = userRepository.getByUserId(userId);
        if (userViewOptional.isEmpty()) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return userViewOptional.get();
    }

    @Override
    public UserRest updateUser(String userId, UserDto user) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);
        if (optionalUserEntity.isEmpty()) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedUser = userRepository.save(userEntity);
        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(updatedUser, returnValue);
        return returnValue;
    }

    @Override
    public void deleteUser(String userId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);
        if (optionalUserEntity.isEmpty()) {
            throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        userRepository.delete(optionalUserEntity.get());
    }

    @Override
    public List<UserView> getUsers(int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);
        return userRepository.getAllBy(pageableRequest);
    }
}
