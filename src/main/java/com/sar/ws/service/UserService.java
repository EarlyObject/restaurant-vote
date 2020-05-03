package com.sar.ws.service;

import com.sar.ws.shared.dto.UserDto;
import com.sar.ws.shared.view.UserView;
import com.sar.ws.ui.model.response.UserRest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserRest create(UserDto user);

    UserDto getUser(String email);

    UserView getByUserId(String userId);

    UserRest updateUser(String userId, UserDto user);

    void deleteUser(String userId);

    List<UserView> getUsers(int page, int limit);
}
