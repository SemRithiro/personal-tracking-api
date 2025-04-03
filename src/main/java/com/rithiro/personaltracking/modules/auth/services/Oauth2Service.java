package com.rithiro.personaltracking.modules.auth.services;

import com.rithiro.personaltracking.modules.auth.models.databases.Oauth2RefreshToken;
import com.rithiro.personaltracking.modules.auth.models.databases.Oauth2RefreshTokenUsage;
import com.rithiro.personaltracking.modules.auth.models.databases.Oauth2Token;

public interface Oauth2Service {

    public int revokeCurrentToken();

    public int revokeOtherTokenByUserId();

    public int revokeAllTokenByUserId();

    public int insertOauth2AccessToken(Oauth2Token oauth2Token);

    public int insertOauth2RefreshToken(Oauth2Token oauth2Token);

    public int insertOauth2RefreshTokenUsage(Oauth2RefreshTokenUsage oauth2RefreshTokenUsage);

    public Oauth2RefreshToken validateRefreshToken(String refreshToken);

}
