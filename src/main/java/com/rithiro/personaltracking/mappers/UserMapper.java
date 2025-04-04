package com.rithiro.personaltracking.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.rithiro.personaltracking.models.databases.Auth;
import com.rithiro.personaltracking.models.databases.User;

@Mapper
public interface UserMapper {
    public Auth findByUsername(@Param("username") String username);

    public User findUserByAuthId(@Param("authId") String authId);
}
