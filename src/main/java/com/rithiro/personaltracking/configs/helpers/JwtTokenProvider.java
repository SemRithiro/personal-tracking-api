package com.rithiro.personaltracking.configs.helpers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.security.oauth2.resourceserver.jwt.jwk-set-uri}")
    private String jwkSetUri;

    private final RSAKeyProperties rsaKeyProperties;

    @Bean
    @Primary
    JwtEncoder accessTokenJwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeyProperties.getAccessPublicKey())
                .privateKey(rsaKeyProperties.getAccessPrivateKey())
                .build();
        JWKSet jwkSet = new JWKSet(jwk);
        JWKSource<SecurityContext> jwkSource = (jwtSelector, context) -> jwtSelector.select(jwkSet);
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    @Primary
    JwtDecoder primaryAccessTokenJwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getAccessPublicKey()).build();
    }

    @Bean
    JwtDecoder secondaryAccessTokenJwtDecoder() {
        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }

    @Bean
    JwtDecoder delegatingJwtDecoder() {
        return new DelegatingJwtDecoder(primaryAccessTokenJwtDecoder(), secondaryAccessTokenJwtDecoder());
    }

    @Bean
    @Qualifier("refreshTokenJwtEncoder")
    JwtEncoder refreshTokenJwtEncoder() {
        JWK jwk = new RSAKey.Builder(rsaKeyProperties.getRefreshPublicKey())
                .privateKey(rsaKeyProperties.getRefreshPrivateKey())
                .build();
        JWKSet jwkSet = new JWKSet(jwk);
        JWKSource<SecurityContext> jwkSource = (jwtSelector, context) -> jwtSelector.select(jwkSet);
        return new NimbusJwtEncoder(jwkSource);
    }

    @Bean
    @Qualifier("refreshTokenJwtDecoder")
    public JwtDecoder refreshTokenJwtDecoder() {
        return NimbusJwtDecoder.withPublicKey(rsaKeyProperties.getRefreshPublicKey()).build();
    }
}
