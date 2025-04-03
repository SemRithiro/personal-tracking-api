package com.rithiro.personaltracking.modules.auth.services;

import com.rithiro.personaltracking.modules.auth.models.databases.Auth;

public interface AuthService {
    public Auth findByUsername(String username);
}
