package com.rithiro.personaltracking.modules.auth.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.rithiro.personaltracking.modules.auth.models.databases.Auth;

@Mapper
public interface AuthMapper {
    public Auth findByUsername(@Param("username") String username);
}
