package com.earlyobject.ws;

import com.earlyobject.ws.entity.Authority;
import com.earlyobject.ws.entity.Role;
import com.earlyobject.ws.entity.UserEntity;
import com.earlyobject.ws.shared.Roles;
import com.earlyobject.ws.shared.dto.Utils;
import com.earlyobject.ws.io.repositories.AuthorityRepository;
import com.earlyobject.ws.io.repositories.RoleRepository;
import com.earlyobject.ws.io.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Component
public class InitialUserSetup {

    @Autowired
    UserRepository userRepository;
    @Autowired
    AuthorityRepository authorityRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    Utils utils;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @EventListener
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        System.out.println("From Application ready event...");
        Authority readAuthority = createAuthority("READ_AUTHORITY");
        Authority writeAuthority = createAuthority("WRITE_AUTHORITY");
        Authority deleteAuthority = createAuthority("DELETE_AUTHORITY");

        createRole(Roles.ROLE_USER.name(), Arrays.asList(readAuthority, writeAuthority));
        Role roleAdmin = createRole(Roles.ROLE_ADMIN.name(), Arrays.asList(readAuthority, writeAuthority, deleteAuthority));

        if (roleAdmin == null) {
            return;
        }

        if (userRepository.findByEmail("admin@test.com") == null) {
            UserEntity adminUser = new UserEntity();
            adminUser.setFirstName("Admin");
            adminUser.setLastName("DefaultAdmin");
            adminUser.setEmail("admin@test.com");
            adminUser.setUserId(utils.generateUserId(30));
            adminUser.setEncryptedPassword(bCryptPasswordEncoder.encode("123"));
            adminUser.setRoles(Collections.singletonList(roleAdmin));

            userRepository.save(adminUser);
        }

    }

    @Transactional
    Authority createAuthority(String name) {
        Authority authority = authorityRepository.findByName(name);
        if (authority == null) {
            authority = new Authority(name);
            authorityRepository.save(authority);
        }
        return authority;
    }

    @Transactional
    Role createRole(String name, Collection<Authority> authorities) {
        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            role.setAuthorities(authorities);
            roleRepository.save(role);
        }
        return role;
    }
}
