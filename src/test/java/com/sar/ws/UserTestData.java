package com.sar.ws;

import com.sar.ws.shared.Roles;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class UserTestData {
    public static final String USER_ID = "testUserId123";
    public static final String FIRST_NAME = "TestFirstName";
    public static final String LAST_NAME = "TestLastName";
    public static final String EMAIL = "test@testmail.com";
    public static final String PASSWORD = "12345678";
    public static final String ENCRYPTED_PASSWORD = "testEncPassword13579";
    public static final Set<Roles> ROLES = new HashSet<>(Collections.singletonList(Roles.ROLE_USER));
}
