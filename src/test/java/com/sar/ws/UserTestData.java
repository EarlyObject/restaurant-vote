package com.sar.ws;

import com.sar.ws.io.entity.Role;
import com.sar.ws.io.entity.UserEntity;
import com.sar.ws.io.entity.Vote;
import com.sar.ws.shared.Roles;
import com.sar.ws.shared.view.UserView;
import com.sar.ws.ui.model.response.UserRest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.*;

import static com.sar.ws.VoteTestData.VOTE;

public class UserTestData {
    public static final long ID = 1010;
    public static final String USER_ID = "testUserId123";
    public static final String FIRST_NAME = "TestFirstName";
    public static final String LAST_NAME = "TestLastName";
    public static final String EMAIL = "test@testmail.com";
    public static final String PASSWORD = "12345678";
    public static final String ENCRYPTED_PASSWORD = "testEncPassword13579";
    public static final List<Vote> VOTES = new ArrayList<>(Collections.singletonList(VOTE));

    public static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(USER_ID);
        userEntity.setFirstName(FIRST_NAME);
        userEntity.setLastName(LAST_NAME);
        userEntity.setEmail(EMAIL);
        userEntity.setEncryptedPassword(ENCRYPTED_PASSWORD);
        userEntity.setEmailVerificationStatus(true);

        Role role = new Role(Roles.ROLE_USER.name());
        Set<Role> roleSet = new HashSet<>(Collections.singletonList(role));
        userEntity.setRoles(roleSet);
        userEntity.setVotes(VOTES);
        return userEntity;
    }

    public static UserView getUserView() {
        ProjectionFactory factory = new SpelAwareProxyProjectionFactory();
        UserView userView = factory.createProjection(UserView.class);
        userView.setUserId(USER_ID);
        userView.setFirstName(FIRST_NAME);
        userView.setLastName(LAST_NAME);
        userView.setEmail(EMAIL);
        return userView;
    }

    public static UserRest getUserRest() {
        UserRest userRest = new UserRest();
        userRest.setUserId(USER_ID);
        userRest.setFirstName(FIRST_NAME);
        userRest.setLastName(LAST_NAME);
        userRest.setEmail(EMAIL);
        return userRest;
    }
}
