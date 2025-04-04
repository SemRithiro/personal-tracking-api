package com.rithiro.personaltracking.models.databases;

import java.time.Instant;

import lombok.Data;

@Data
public class Oauth2Token {
    private String tokenId;
    private String tokenValue;
    private Integer userId;
    private Instant issuedAt;
    private Instant expiresAt;
    private String userAgent;
    private String operatingSystem;
    private String remoteAddr;
}
