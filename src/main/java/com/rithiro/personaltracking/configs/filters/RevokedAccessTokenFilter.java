package com.rithiro.personaltracking.configs.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.rithiro.personaltracking.models.databases.Oauth2AccessToken;
import com.rithiro.personaltracking.services.Oauth2AccessTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class RevokedAccessTokenFilter extends OncePerRequestFilter {

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    @Autowired
    Oauth2AccessTokenService oauth2AccessTokenService;

    @Override
    @SuppressWarnings("null")
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() != "anonymousUser"
                && authentication.isAuthenticated()) {
            Jwt jwt = (Jwt) authentication.getCredentials();
            if (!(jwt.getClaimAsString("iss").equals(issuerUri))) {
                Oauth2AccessToken oauth2AccessToken = oauth2AccessTokenService
                        .verifyRevokedAccessToken(jwt.getId());
                if (oauth2AccessToken == null || oauth2AccessToken.getRevoked()) {
                    response.setStatus(HttpStatus.UNAUTHORIZED.value());
                    response.getWriter().flush();
                    return;
                }
            }
        }
        filterChain.doFilter(request, response);
    }

}
