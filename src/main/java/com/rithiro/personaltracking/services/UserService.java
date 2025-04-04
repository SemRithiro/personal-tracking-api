package com.rithiro.personaltracking.services;

import com.rithiro.personaltracking.models.databases.Auth;
import com.rithiro.personaltracking.models.responses.UserResponse;

public interface UserService {
    public Auth findByUsername(String username);

    public UserResponse findUserByAuthId(String authId);
}
