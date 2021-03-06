package com.earlyobject.ws.service;

import com.earlyobject.ws.entity.Role;
import com.earlyobject.ws.entity.UserEntity;
import com.earlyobject.ws.exceptions.NotFoundException;
import com.earlyobject.ws.io.repositories.RoleRepository;
import com.earlyobject.ws.io.repositories.UserRepository;
import com.earlyobject.ws.security.UserPrincipal;
import com.earlyobject.ws.shared.dto.UserDto;
import com.earlyobject.ws.shared.dto.Utils;
import com.earlyobject.ws.shared.view.UserView;
import com.earlyobject.ws.ui.model.response.ErrorMessages;
import com.earlyobject.ws.ui.model.response.UserRest;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Transactional
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

    public UserDto get(String email) {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }
        UserDto returnValue = new UserDto();
        BeanUtils.copyProperties(userEntity.get(), returnValue);
        return returnValue;
    }

    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<UserEntity> userEntity = userRepository.findByEmail(email);
        if (userEntity.isEmpty()) {
            throw new UsernameNotFoundException("User with email: " + email + " not found");
        }
        return new UserPrincipal(userEntity.get());
    }

    public UserView getByUserId(String userId) {
        Optional<UserView> userViewOptional = userRepository.getByUserId(userId);
        if (userViewOptional.isEmpty()) {
            throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        return userViewOptional.get();
    }

    @Transactional
    public UserRest update(String userId, UserDto user) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);
        if (optionalUserEntity.isEmpty()) {
            throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserEntity userEntity = optionalUserEntity.get();
        userEntity.setFirstName(user.getFirstName());
        userEntity.setLastName(user.getLastName());

        UserEntity updatedUser = userRepository.save(userEntity);
        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(updatedUser, returnValue);
        return returnValue;
    }

    @Transactional
    public UserRest updateToAdmin(String userId, UserDto userDto) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);
        if (optionalUserEntity.isEmpty()) {
            throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        UserEntity userEntity = optionalUserEntity.get();
        Collection<Role> roleEntities = new HashSet<>();
        for (String role : userDto.getRoles()) {
            Role roleEntity = roleRepository.findByName(role);
            if (roleEntity != null) {
                roleEntities.add(roleEntity);
            }
        }
        userEntity.setRoles(roleEntities);
        UserEntity updatedUser = userRepository.save(userEntity);
        UserRest returnValue = new UserRest();
        BeanUtils.copyProperties(updatedUser, returnValue);
        return returnValue;
    }

    @Transactional
    public void delete(String userId) {
        Optional<UserEntity> optionalUserEntity = userRepository.findByUserId(userId);
        if (optionalUserEntity.isEmpty()) {
            throw new NotFoundException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        }
        userRepository.delete(optionalUserEntity.get());
    }

    public List<UserView> getAll(int page, int limit) {
        Pageable pageableRequest = PageRequest.of(page, limit);
        return userRepository.getAllBy(pageableRequest);
    }
}
