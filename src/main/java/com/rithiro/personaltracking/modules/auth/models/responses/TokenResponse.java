package com.rithiro.personaltracking.modules.auth.models.responses;

import lombok.Data;

@Data
public class TokenResponse {
    private String accessToken;
    private String refreshToken;
}
