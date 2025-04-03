package com.rithiro.personaltracking.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.rithiro.personaltracking.models.databases.Oauth2AccessToken;

@Mapper
public interface Oauth2AccessTokenMapper {
    public Oauth2AccessToken verifyRevokedAccessToken(@Param("tokenId") String tokenId);
}
