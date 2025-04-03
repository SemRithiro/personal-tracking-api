package com.rithiro.personaltracking.modules.auth.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import com.rithiro.personaltracking.modules.auth.mappers.Oauth2Mapper;
import com.rithiro.personaltracking.modules.auth.models.databases.Auth;
import com.rithiro.personaltracking.modules.auth.models.databases.Oauth2RefreshToken;
import com.rithiro.personaltracking.modules.auth.models.databases.Oauth2RefreshTokenUsage;
import com.rithiro.personaltracking.modules.auth.models.databases.Oauth2Token;
import com.rithiro.personaltracking.modules.auth.services.AuthService;
import com.rithiro.personaltracking.modules.auth.services.Oauth2Service;

@Service
@Primary
public class Oauth2ServiceImpl implements Oauth2Service {

    @Autowired
    Oauth2Mapper oauth2Mapper;

    @Autowired
    AuthService authService;

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
    public Oauth2RefreshToken validateRefreshToken(String refreshToken) {
        return oauth2Mapper.validateRefreshToken(refreshToken);
    }

    @Override
    public int revokeCurrentToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();

        return oauth2Mapper.revokeCurrentToken(jwt.getId());
    }

    @Override
    public int revokeOtherTokenByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();
        Auth authUser = authService.findByUsername(jwt.getClaimAsString("sub"));

        if (authUser != null)
            return oauth2Mapper.revokeOtherTokenByUserId(jwt.getId(), authUser.getId());
        else
            return 0;
    }

    @Override
    public int revokeAllTokenByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();
        Auth authUser = authService.findByUsername(jwt.getClaimAsString("sub"));

        if (authUser != null)
            return oauth2Mapper.revokeAllTokenByUserId(authUser.getId());
        else
            return 0;
    }
}
