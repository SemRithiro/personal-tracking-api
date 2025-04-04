package com.rithiro.personaltracking.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RefreshTokenRequest {
    @NotNull
    private String refreshToken;
}
