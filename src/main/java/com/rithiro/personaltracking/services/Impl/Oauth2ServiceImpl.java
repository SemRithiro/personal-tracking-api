package com.rithiro.personaltracking.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.rithiro.personaltracking.mappers.Oauth2Mapper;
import com.rithiro.personaltracking.models.databases.Oauth2AccessToken;
import com.rithiro.personaltracking.models.databases.Oauth2RefreshToken;
import com.rithiro.personaltracking.models.databases.Oauth2RefreshTokenUsage;
import com.rithiro.personaltracking.models.databases.Oauth2Token;
import com.rithiro.personaltracking.services.Oauth2Service;

@Service
@Primary
public class Oauth2ServiceImpl implements Oauth2Service {

    @Autowired
    Oauth2Mapper oauth2Mapper;

    @Override
    public int insertOauth2AccessToken(Oauth2Token oauth2Token) {
        return oauth2Mapper.insertOauth2AccessToken(oauth2Token);
    }

    @Override
    public int insertOauth2RefreshToken(Oauth2Token oauth2Token) {
        return oauth2Mapper.insertOauth2RefreshToken(oauth2Token);
    }

    @Override
    public int insertOauth2RefreshTokenUsage(Oauth2RefreshTokenUsage oauth2RefreshTokenUsage) {
        return oauth2Mapper.insertOauth2RefreshTokenUsage(oauth2RefreshTokenUsage);
    }

    @Override
    public int revokeCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();

        return oauth2Mapper.revokeCurrentToken(jwt.getId());
    }

    @Override
    public int revokeOtherTokenByUserId(String jti, Integer userId) {
        return oauth2Mapper.revokeOtherTokenByUserId(jti, userId);
    }

    @Override
    public int revokeAllTokenByUserId(Integer userId) {
        return oauth2Mapper.revokeAllTokenByUserId(userId);
    }

    @Override
    public Oauth2AccessToken verifyRevokedAccessToken(String tokenId) {
        return oauth2Mapper.verifyRevokedAccessToken(tokenId);
    }

    @Override
    public Oauth2RefreshToken validateRefreshToken(String refreshToken) {
        return oauth2Mapper.validateRefreshToken(refreshToken);
    }
}
