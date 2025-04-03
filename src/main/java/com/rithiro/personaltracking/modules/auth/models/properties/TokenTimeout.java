package com.rithiro.personaltracking.modules.auth.models.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("token")
public class TokenTimeout {
    private long accessTokenTimeout;
    private long refreshTokenTimeout;
}
