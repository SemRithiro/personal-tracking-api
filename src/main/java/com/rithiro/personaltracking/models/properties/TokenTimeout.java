package com.rithiro.personaltracking.models.properties;

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
