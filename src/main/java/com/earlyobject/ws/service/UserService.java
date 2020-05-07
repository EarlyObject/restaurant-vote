package com.earlyobject.ws.service;

import com.earlyobject.ws.shared.dto.UserDto;
import com.earlyobject.ws.shared.view.UserView;
import com.earlyobject.ws.ui.model.response.UserRest;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserRest create(UserDto user);

    UserDto get(String email);

    UserView getByUserId(String userId);

    UserRest update(String userId, UserDto user);

    void delete(String userId);

    List<UserView> getAll(int page, int limit);

    UserRest updateToAdmin(String userId, UserDto userDto);
}
