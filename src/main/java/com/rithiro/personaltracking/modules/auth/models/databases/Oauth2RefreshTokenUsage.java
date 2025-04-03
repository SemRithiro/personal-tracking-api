package com.rithiro.personaltracking.modules.auth.models.databases;

import lombok.Data;

@Data
public class Oauth2RefreshTokenUsage {
    private String tokenId;
    private String tokenValue;
    private String userAgent;
    private String operatingSystem;
    private String remoteAddr;
}
