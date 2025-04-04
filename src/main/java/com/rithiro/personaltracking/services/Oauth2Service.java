package com.rithiro.personaltracking.services;

import com.rithiro.personaltracking.models.databases.Oauth2AccessToken;
import com.rithiro.personaltracking.models.databases.Oauth2RefreshToken;
import com.rithiro.personaltracking.models.databases.Oauth2RefreshTokenUsage;
import com.rithiro.personaltracking.models.databases.Oauth2Token;

public interface Oauth2Service {

    public int revokeCurrentToken();

    public int revokeOtherTokenByUserId(String jti, Integer userId);

    public int revokeAllTokenByUserId(Integer userId);

    public int insertOauth2AccessToken(Oauth2Token oauth2Token);

    public int insertOauth2RefreshToken(Oauth2Token oauth2Token);

    public int insertOauth2RefreshTokenUsage(Oauth2RefreshTokenUsage oauth2RefreshTokenUsage);

    public Oauth2AccessToken verifyRevokedAccessToken(String tokenId);

    public Oauth2RefreshToken validateRefreshToken(String refreshToken);

}
