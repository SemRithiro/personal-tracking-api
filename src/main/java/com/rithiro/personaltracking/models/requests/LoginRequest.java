package com.rithiro.personaltracking.models.requests;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotNull
    private String username;

    @NotNull
    private String password;
}
