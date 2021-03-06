package com.earlyobject.ws;

import com.earlyobject.ws.entity.Role;
import com.earlyobject.ws.entity.UserEntity;
import com.earlyobject.ws.entity.Vote;
import com.earlyobject.ws.shared.Roles;
import com.earlyobject.ws.shared.view.UserView;
import com.earlyobject.ws.ui.model.response.UserRest;
import org.springframework.data.projection.ProjectionFactory;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;

import java.util.*;

import static com.earlyobject.ws.VoteTestData.VOTE;

public class UserTestData {
    public static final long ID = 1020;
    public static final String USER_ID = "dbkpmrTiPq1MbmFaK4LD";
    public static final String FIRST_NAME = "UserName";
    public static final String LAST_NAME = "UserLastName";
    public static final String EMAIL = "test@test.com";
    public static final String PASSWORD = "321";
    public static final String ENCRYPTED_PASSWORD = "$2a$10$A2CgSZVPOn03bs.AerYRFuwz39fkRQ2jIkHkUryv0PPmBgpcrwRCC";
    public static final List<Vote> VOTES = new ArrayList<>(Collections.singletonList(VOTE));

    public static UserEntity getUserEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(USER_ID);
        userEntity.setFirstName(FIRST_NAME);
        userEntity.setLastName(LAST_NAME);
        userEntity.setEmail(EMAIL);
        userEntity.setEncryptedPassword(ENCRYPTED_PASSWORD);

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
