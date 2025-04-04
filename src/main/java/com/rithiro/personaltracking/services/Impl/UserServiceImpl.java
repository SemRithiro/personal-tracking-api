package com.rithiro.personaltracking.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rithiro.personaltracking.cache.UserCaches;
import com.rithiro.personaltracking.mappers.UserMapper;
import com.rithiro.personaltracking.models.databases.Auth;
import com.rithiro.personaltracking.models.databases.User;
import com.rithiro.personaltracking.models.responses.UserResponse;
import com.rithiro.personaltracking.services.UserService;

@Service
@Primary
public class UserServiceImpl implements UserService {

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    UserMapper userMapper;

    @Override
    public Auth findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public UserResponse findUserByAuthId(String authId) {
        User user = UserCaches.findUserByAuthId(authId);
        if (user == null) {
            user = userMapper.findUserByAuthId(authId);
            if (user != null)
                UserCaches.updateAuthUser(authId, user);
        }

        return objectMapper.convertValue(user, UserResponse.class);
    }

}
