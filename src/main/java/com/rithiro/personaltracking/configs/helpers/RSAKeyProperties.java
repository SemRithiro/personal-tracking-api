package com.rithiro.personaltracking.configs.helpers;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

import lombok.Data;

@Data
@ConfigurationProperties(prefix = "rsakey")
@ConfigurationPropertiesScan
public class RSAKeyProperties {
    private RSAPublicKey accessPublicKey;
    private RSAPrivateKey accessPrivateKey;
    private RSAPublicKey refreshPublicKey;
    private RSAPrivateKey refreshPrivateKey;
}
