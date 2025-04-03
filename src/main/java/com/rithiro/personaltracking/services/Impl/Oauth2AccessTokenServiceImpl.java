package com.rithiro.personaltracking.services.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.rithiro.personaltracking.mappers.Oauth2AccessTokenMapper;
import com.rithiro.personaltracking.models.databases.Oauth2AccessToken;
import com.rithiro.personaltracking.services.Oauth2AccessTokenService;

@Service
@Primary
public class Oauth2AccessTokenServiceImpl implements Oauth2AccessTokenService {

    @Autowired
    Oauth2AccessTokenMapper oauth2AccessTokenMapper;

    @Override
    public Oauth2AccessToken verifyRevokedAccessToken(String tokenId) {
        return oauth2AccessTokenMapper.verifyRevokedAccessToken(tokenId);
    }

}
