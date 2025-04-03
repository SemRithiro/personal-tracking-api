package com.rithiro.personaltracking.configs.helpers;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;

public class DelegatingJwtDecoder implements JwtDecoder {

    private final JwtDecoder primaryAccessTokenDecoder;
    private final JwtDecoder secondaryAccessTokenDecoder;

    public DelegatingJwtDecoder(JwtDecoder primaryAccessTokenDecoder, JwtDecoder secondaryAccessTokenDecoder) {
        this.primaryAccessTokenDecoder = primaryAccessTokenDecoder;
        this.secondaryAccessTokenDecoder = secondaryAccessTokenDecoder;
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        try {
            return primaryAccessTokenDecoder.decode(token);
        } catch (JwtException e) {
            return secondaryAccessTokenDecoder.decode(token);
        }
    }

}
