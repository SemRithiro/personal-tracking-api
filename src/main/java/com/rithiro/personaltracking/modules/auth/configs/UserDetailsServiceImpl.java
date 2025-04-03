package com.rithiro.personaltracking.modules.auth.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.rithiro.personaltracking.modules.auth.mappers.AuthMapper;
import com.rithiro.personaltracking.modules.auth.models.databases.Auth;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private AuthMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Auth auth = authMapper.findByUsername(username);
        if (auth != null) {
            return new UserDetailsImpl(auth);
        } else
            throw new UsernameNotFoundException("Username not found " + username);
    }
}
