package com.rithiro.personaltracking.modules.auth.mappers;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.rithiro.personaltracking.modules.auth.models.databases.Oauth2RefreshToken;
import com.rithiro.personaltracking.modules.auth.models.databases.Oauth2RefreshTokenUsage;
import com.rithiro.personaltracking.modules.auth.models.databases.Oauth2Token;

@Mapper
public interface Oauth2Mapper {

    public int revokeCurrentToken(@Param("tokenId") String tokenId);

    public int revokeOtherTokenByUserId(@Param("tokenId") String tokenId, @Param("userId") Integer userId);

    public int revokeAllTokenByUserId(@Param("userId") Integer userId);

    public int insertOauth2AccessToken(@Param("oauth2Token") Oauth2Token oauth2Token);

    public int insertOauth2RefreshToken(@Param("oauth2Token") Oauth2Token oauth2Token);

    public int insertOauth2RefreshTokenUsage(
            @Param("oauth2RefreshTokenUsage") Oauth2RefreshTokenUsage oauth2RefreshTokenUsage);

    public Oauth2RefreshToken validateRefreshToken(@Param("tokenValue") String refreshToken);
}
