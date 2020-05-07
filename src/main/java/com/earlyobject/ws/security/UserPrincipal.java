package com.earlyobject.ws.security;

import com.earlyobject.ws.entity.Authority;
import com.earlyobject.ws.entity.Role;
import com.earlyobject.ws.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;

@Service
public class UserPrincipal implements UserDetails {

    private static final long serialVersionUID = 7697357093063978082L;

    private UserEntity userEntity;
    private String userId;

    public UserPrincipal() {
    }

    public UserPrincipal(UserEntity userEntity) {
        this.userEntity = userEntity;
        this.userId = userEntity.getUserId();
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> authorities = new HashSet<>();
        Collection<Authority> authorityEntities = new HashSet<>();

        //Get user Roles
        Collection<Role> roles = userEntity.getRoles();

        if (roles == null) {
            return authorities;
        } else {
            roles.forEach((role) -> {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
                authorityEntities.addAll(role.getAuthorities());
            });
        }
        authorityEntities.forEach((authority) -> {
            authorities.add(new SimpleGrantedAuthority(authority.getName()));
        });

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.userEntity.getEncryptedPassword();
    }

    @Override
    public String getUsername() {
        return this.userEntity.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.userEntity.getEmailVerificationStatus();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
