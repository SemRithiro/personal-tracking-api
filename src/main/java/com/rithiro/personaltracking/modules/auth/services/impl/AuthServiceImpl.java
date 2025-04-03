package com.rithiro.personaltracking.modules.auth.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.rithiro.personaltracking.modules.auth.mappers.AuthMapper;
import com.rithiro.personaltracking.modules.auth.models.databases.Auth;
import com.rithiro.personaltracking.modules.auth.services.AuthService;

@Service
@Primary
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    @Override
    public Auth findByUsername(String username) {
        return authMapper.findByUsername(username);
    }

}
