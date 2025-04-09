package com.rithiro.personaltracking.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rithiro.personaltracking.models.databases.Auth;
import com.rithiro.personaltracking.services.UserService;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth auth = userService.findByUsername(username);
        if (auth != null) {
            return new UserDetailsImpl(auth);
        } else
            throw new UsernameNotFoundException("Username not found " + username);
    }
}
