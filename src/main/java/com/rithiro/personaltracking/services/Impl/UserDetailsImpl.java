package com.rithiro.personaltracking.services.impl;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.rithiro.personaltracking.models.databases.Auth;

public class UserDetailsImpl implements UserDetails {

    private Auth auth;

    public UserDetailsImpl(Auth auth) {
        this.auth = auth;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String roles = "ADMIN";
        return Arrays.stream(roles.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return auth.getPassword();
    }

    @Override
    public String getUsername() {
        return auth.getUsername();
    }

}
