package com.rithiro.personaltracking.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter.ReferrerPolicy;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter.HeaderValue;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.rithiro.personaltracking.configs.helpers.JwtTokenProvider;
import com.rithiro.personaltracking.models.properties.CorsProperties;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final UserDetailsService userDetailsService;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    CorsProperties corsProperties;

    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(new BCryptPasswordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

    @Bean
    @Qualifier("jwtRefreshTokenAuthProvider")
    JwtAuthenticationProvider jwtRefreshTokenAuthProvider() {
        JwtAuthenticationProvider jwtAuthenticationProvider = new JwtAuthenticationProvider(
                jwtTokenProvider.refreshTokenJwtDecoder());
        jwtAuthenticationProvider.setJwtAuthenticationConverter(jwtAuthenticationConverter());
        return jwtAuthenticationProvider;
    }

    @Bean
    CorsConfigurationSource configurationSource() {
        CorsConfiguration cors = new CorsConfiguration();
        cors.applyPermitDefaultValues();
        cors.setAllowedOrigins(corsProperties.getAllowedOrigins());
        cors.setAllowedMethods(corsProperties.getAllowedMethods());
        cors.setAllowedHeaders(corsProperties.getAllowedHeaders());
        cors.setAllowCredentials(corsProperties.isAllowCredentials());
        cors.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", cors);
        return source;
    }

    void configureSecurityHeaders(HeadersConfigurer<HttpSecurity> headers) {
        headers.httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true).maxAgeInSeconds(31536000))
                .contentTypeOptions(contentTypeOptions -> contentTypeOptions.disable())
                .frameOptions(frameOptions -> frameOptions.deny())
                .xssProtection(xss -> xss.headerValue(HeaderValue.ENABLED_MODE_BLOCK))
                .referrerPolicy(referrerPolicy -> referrerPolicy.policy(ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
                .contentSecurityPolicy(csp -> csp.policyDirectives(
                        "default-src 'self'; script-src 'self'; object-src 'none'; style-src 'self' 'unsafe-inline';"))
                .permissionsPolicyHeader(permissions -> permissions.policy("geolocation=(), microphone=(), camera=()"));
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationEntryPoint authEntryPoint)
            throws Exception {
        http
                // .csrf(csrf ->
                // csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(configurationSource()))
                .headers(headers -> configureSecurityHeaders(headers))
                .securityMatcher("/**")
                .authorizeHttpRequests(oauth -> oauth
                        .requestMatchers("/actuator/**").permitAll()
                        .requestMatchers("/", "/auth/login", "/auth/refresh-token").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**").permitAll()
                        .requestMatchers("/uploads/**").permitAll()
                        .anyRequest().authenticated())
                .exceptionHandling(ex -> ex.accessDeniedHandler(new CustomAccessDeniedHandler()))
                .oauth2ResourceServer(
                        oauth2 -> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
                                .authenticationEntryPoint(authEntryPoint))
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable);
        return http.build();
    }
}
