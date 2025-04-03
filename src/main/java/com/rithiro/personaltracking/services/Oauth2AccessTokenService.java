package com.rithiro.personaltracking.services;

import com.rithiro.personaltracking.models.databases.Oauth2AccessToken;

public interface Oauth2AccessTokenService {
    public Oauth2AccessToken verifyRevokedAccessToken(String tokenId);
}
