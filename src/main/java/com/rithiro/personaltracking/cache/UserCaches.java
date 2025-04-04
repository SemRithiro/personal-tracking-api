package com.rithiro.personaltracking.cache;

import java.util.concurrent.ConcurrentHashMap;

import com.rithiro.personaltracking.models.databases.User;

public class UserCaches {
    private static final ConcurrentHashMap<String, User> userCaches = new ConcurrentHashMap<>();

    public static void updateAuthUser(String authId, User user) {
        userCaches.put(authId, user);
    }

    public static User findUserByAuthId(String authId) {
        return userCaches.get(authId);
    }
}
