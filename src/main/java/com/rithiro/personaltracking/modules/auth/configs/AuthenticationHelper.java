package com.rithiro.personaltracking.modules.auth.configs;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rithiro.personaltracking.modules.auth.mappers.AuthMapper;
import com.rithiro.personaltracking.modules.auth.models.databases.Auth;
import com.rithiro.personaltracking.modules.auth.models.databases.Oauth2RefreshTokenUsage;
import com.rithiro.personaltracking.modules.auth.models.databases.Oauth2Token;
import com.rithiro.personaltracking.modules.auth.models.properties.TokenTimeout;
import com.rithiro.personaltracking.modules.auth.models.requests.ClientInfoRequest;
import com.rithiro.personaltracking.modules.auth.models.requests.LoginRequest;
import com.rithiro.personaltracking.modules.auth.models.requests.RefreshTokenRequest;
import com.rithiro.personaltracking.modules.auth.models.requests.TokenInfoRequest;
import com.rithiro.personaltracking.modules.auth.models.responses.TokenResponse;
import com.rithiro.personaltracking.modules.auth.services.Oauth2Service;
import com.rithiro.personaltracking.utils.Helper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthenticationHelper {

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	AuthMapper authMapper;

	@Autowired
	Oauth2Service oauth2Service;

	private final JwtEncoder acessTokenJwtEncoder;
	private JwtEncoder refreshTokenJwtEncoder;

	private final DaoAuthenticationProvider daoAuthenticationProvider;
	private final JwtAuthenticationProvider jwtAuthenticationProvider;

	private final TokenTimeout tokenTimeout;

	@Value("${spring.application.name}")
	String ISSUER_ID;

	@Autowired
	public void setRefreshTokenJwtEncoder(@Qualifier("refreshTokenJwtEncoder") JwtEncoder refreshTokenJwtEncoder) {
		this.refreshTokenJwtEncoder = refreshTokenJwtEncoder;
	}

	public TokenResponse login(LoginRequest loginRequest, ClientInfoRequest clientInfoRequest) {
		Authentication authentication = new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
				loginRequest.getPassword());
		authentication = daoAuthenticationProvider.authenticate(authentication);

		TokenResponse projectToken = new TokenResponse();
		TokenInfoRequest accessTokenInfo = new TokenInfoRequest();
		TokenInfoRequest refreshTokenInfo = new TokenInfoRequest();

		Instant now = Instant.now();
		String jwtId = UUID.randomUUID().toString();

		Auth authUser = authMapper.findByUsername(loginRequest.getUsername());

		if (authUser != null) {
			accessTokenInfo.setIssuer(ISSUER_ID);
			accessTokenInfo.setTokenId(jwtId);
			accessTokenInfo.setUserId(authUser.getId());
			accessTokenInfo.setSubject(authentication.getName());
			accessTokenInfo.setRoles(authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.filter(role -> !role.startsWith("ROLE_"))
					.toList());
			accessTokenInfo.setIssuedAt(now);
			accessTokenInfo.setExpiresAt(now.plus(tokenTimeout.getAccessTokenTimeout(), ChronoUnit.MINUTES));

			refreshTokenInfo = objectMapper.convertValue(accessTokenInfo, TokenInfoRequest.class);
			refreshTokenInfo.setRoles(List.of("REFRESH_TOKEN"));
			refreshTokenInfo.setExpiresAt(now.plus(tokenTimeout.getRefreshTokenTimeout(), ChronoUnit.MINUTES));

			projectToken.setAccessToken(generateAccessTokenToken(accessTokenInfo, clientInfoRequest));
			projectToken.setRefreshToken(generateRefreshTokenToken(refreshTokenInfo, clientInfoRequest));

			return projectToken;
		}
		return null;
	}

	public TokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest, ClientInfoRequest clientInfoRequest) {
		BearerTokenAuthenticationToken accessToken = new BearerTokenAuthenticationToken(
				refreshTokenRequest.getRefreshToken());
		Authentication authentication = jwtAuthenticationProvider.authenticate(accessToken);
		Jwt jwt = (Jwt) authentication.getCredentials();

		TokenResponse projectToken = new TokenResponse();
		TokenInfoRequest accessTokenInfo = new TokenInfoRequest();
		TokenInfoRequest refreshTokenInfo = new TokenInfoRequest();

		Instant now = Instant.now();
		String jwtId = UUID.randomUUID().toString();
		String issuer = jwt.getClaimAsString("iss");
		String subject = jwt.getClaimAsString("sub");

		Auth authUser = authMapper.findByUsername(subject);

		if (authUser != null) {
			accessTokenInfo.setIssuer(issuer);
			accessTokenInfo.setTokenId(jwtId);
			accessTokenInfo.setSubject(subject);
			accessTokenInfo.setUserId(authUser.getId());
			accessTokenInfo.setRoles(authentication.getAuthorities().stream()
					.map(GrantedAuthority::getAuthority)
					.filter(role -> !role.startsWith("ROLE_"))
					.toList());
			accessTokenInfo.setIssuedAt(now);
			accessTokenInfo.setExpiresAt(now.plus(tokenTimeout.getAccessTokenTimeout(), ChronoUnit.MINUTES));

			refreshTokenInfo = objectMapper.convertValue(accessTokenInfo, TokenInfoRequest.class);
			refreshTokenInfo.setRoles(List.of("REFRESH_TOKEN"));
			refreshTokenInfo.setExpiresAt(now.plus(tokenTimeout.getRefreshTokenTimeout(), ChronoUnit.MINUTES));

			Oauth2RefreshTokenUsage oauth2RefreshTokenUsage = objectMapper.convertValue(accessToken,
					Oauth2RefreshTokenUsage.class);
			oauth2RefreshTokenUsage.setTokenId(jwt.getId());
			oauth2RefreshTokenUsage.setTokenValue(refreshTokenRequest.getRefreshToken());
			oauth2RefreshTokenUsage.setRemoteAddr(clientInfoRequest.getRemoteAddr());
			oauth2RefreshTokenUsage.setUserAgent(clientInfoRequest.getUserAgent());
			oauth2RefreshTokenUsage.setOperatingSystem(Helper.getOperatingSystem(clientInfoRequest.getUserAgent()));

			oauth2Service.insertOauth2RefreshTokenUsage(oauth2RefreshTokenUsage);

			projectToken.setAccessToken(generateAccessTokenToken(accessTokenInfo, clientInfoRequest));
			projectToken.setRefreshToken(generateRefreshTokenToken(refreshTokenInfo, clientInfoRequest));

			return projectToken;
		}
		return null;
	}

	private String generateAccessTokenToken(TokenInfoRequest tokenInfoRequest, ClientInfoRequest clientInfoRequest) {
		JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
				.issuer(tokenInfoRequest.getIssuer())
				.id(tokenInfoRequest.getTokenId())
				.claim("roles", tokenInfoRequest.getRoles())
				.subject(tokenInfoRequest.getSubject())
				.issuedAt(tokenInfoRequest.getIssuedAt())
				.expiresAt(tokenInfoRequest.getExpiresAt())
				.build();

		String token = acessTokenJwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
		Oauth2Token oauth2Token = objectMapper.convertValue(tokenInfoRequest, Oauth2Token.class);
		oauth2Token.setTokenValue(token);
		oauth2Token.setRemoteAddr(clientInfoRequest.getRemoteAddr());
		oauth2Token.setUserAgent(clientInfoRequest.getUserAgent());
		oauth2Token.setOperatingSystem(Helper.getOperatingSystem(clientInfoRequest.getUserAgent()));

		oauth2Service.insertOauth2AccessToken(oauth2Token);

		return token;
	}

	private String generateRefreshTokenToken(TokenInfoRequest tokenInfoRequest, ClientInfoRequest clientInfoRequest) {
		JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
				.issuer(tokenInfoRequest.getIssuer())
				.id(tokenInfoRequest.getTokenId())
				.claim("roles", tokenInfoRequest.getRoles())
				.subject(tokenInfoRequest.getSubject())
				.issuedAt(tokenInfoRequest.getIssuedAt())
				.notBefore(tokenInfoRequest.getIssuedAt())
				.expiresAt(tokenInfoRequest.getExpiresAt())
				.build();

		String token = refreshTokenJwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
		Oauth2Token oauth2Token = objectMapper.convertValue(tokenInfoRequest, Oauth2Token.class);
		oauth2Token.setTokenValue(token);
		oauth2Token.setRemoteAddr(clientInfoRequest.getRemoteAddr());
		oauth2Token.setUserAgent(clientInfoRequest.getUserAgent());
		oauth2Token.setOperatingSystem(Helper.getOperatingSystem(clientInfoRequest.getUserAgent()));

		oauth2Service.insertOauth2RefreshToken(oauth2Token);

		return token;
	}
}
