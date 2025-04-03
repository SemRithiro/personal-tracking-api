package com.rithiro.personaltracking.modules.auth.models.requests;

import java.time.Instant;
import java.util.List;

import lombok.Data;

@Data
public class TokenInfoRequest {
    private String issuer;
    private String tokenId;
    private Integer userId;
    private String subject;
    private List<String> roles;
    private Instant issuedAt;
    private Instant expiresAt;
}
