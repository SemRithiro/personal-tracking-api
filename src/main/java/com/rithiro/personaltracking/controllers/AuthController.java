package com.rithiro.personaltracking.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rithiro.personaltracking.configs.AuthenticationHelper;
import com.rithiro.personaltracking.models.bases.baseResponses.ResponseMessage;
import com.rithiro.personaltracking.models.databases.Oauth2RefreshToken;
import com.rithiro.personaltracking.models.requests.ClientInfoRequest;
import com.rithiro.personaltracking.models.requests.LoginRequest;
import com.rithiro.personaltracking.models.requests.RefreshTokenRequest;
import com.rithiro.personaltracking.models.responses.TokenResponse;
import com.rithiro.personaltracking.models.responses.UserResponse;
import com.rithiro.personaltracking.services.Oauth2Service;
import com.rithiro.personaltracking.services.UserService;
import com.rithiro.personaltracking.utils.ResponseMessageUtility;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
@RestController
@RequestMapping("/auth")
@Tag(name = "02. Authorization", description = "Authorization controller")
public class AuthController {

    @Autowired
    Oauth2Service oauth2Service;

    @Autowired
    UserService userService;

    @Autowired
    AuthenticationHelper authenticationHelper;

    @PostMapping("/login")
    public ResponseEntity<ResponseMessage<TokenResponse>> login(
            HttpServletRequest request,
            @RequestBody @Valid LoginRequest loginRequest) {

        ClientInfoRequest clientInfoRequest = new ClientInfoRequest();
        clientInfoRequest.setMethod(request.getMethod());
        clientInfoRequest.setRequestURI(request.getRequestURI());
        clientInfoRequest.setRemoteAddr(request.getRemoteAddr());
        clientInfoRequest.setUserAgent(request.getHeader("User-Agent"));

        TokenResponse tokenResponse = authenticationHelper.login(loginRequest, clientInfoRequest);

        return ResponseMessageUtility.makeSuccessResponse(tokenResponse, "Welcome " + loginRequest.getUsername(),
                HttpStatus.OK);
    }

    @PostMapping("/refresh-token")
    public ResponseEntity<ResponseMessage<TokenResponse>> refreshToken(
            HttpServletRequest request,
            @RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {

        Oauth2RefreshToken oauth2RefreshToken = oauth2Service
                .validateRefreshToken(refreshTokenRequest.getRefreshToken());

        if (!oauth2RefreshToken.getRevoked() && oauth2RefreshToken.getUsageCount() == 0) {
            ClientInfoRequest clientInfoRequest = new ClientInfoRequest();
            clientInfoRequest.setMethod(request.getMethod());
            clientInfoRequest.setRequestURI(request.getRequestURI());
            clientInfoRequest.setRemoteAddr(request.getRemoteAddr());
            clientInfoRequest.setUserAgent(request.getHeader("User-Agent"));

            TokenResponse tokenResponse = authenticationHelper.refreshToken(refreshTokenRequest, clientInfoRequest);
            return ResponseMessageUtility.makeSuccessResponse(tokenResponse, "OK", HttpStatus.OK);
        }
        return ResponseMessageUtility.makeUnauthorizedResponse("Refresh token has been revoked or exceed limitation");

    }

    @PostMapping("/logout")
    public ResponseEntity<ResponseMessage<String>> logout() {
        if (oauth2Service.revokeCurrentToken() > 0)
            return ResponseMessageUtility.makeSuccessResponse("OK", HttpStatus.OK);
        else
            return ResponseMessageUtility.makeFailureResponse("Failed to logout", HttpStatus.ACCEPTED);

    }

    @PostMapping("/logout-other-devices")
    public ResponseEntity<ResponseMessage<String>> logoutOtherDevice() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();
        UserResponse userResponse = userService.findUserByAuthId(jwt.getClaimAsString("sub"));

        if (oauth2Service.revokeOtherTokenByUserId(jwt.getId(), userResponse.getId()) > 0)
            return ResponseMessageUtility.makeSuccessResponse("OK", HttpStatus.OK);
        else
            return ResponseMessageUtility.makeFailureResponse("Failed to logout from other devices",
                    HttpStatus.ACCEPTED);

    }

    @PostMapping("/logout-all-devices")
    public ResponseEntity<ResponseMessage<String>> logoutAllDevice() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();
        UserResponse userResponse = userService.findUserByAuthId(jwt.getClaimAsString("sub"));

        if (oauth2Service.revokeAllTokenByUserId(userResponse.getId()) > 0)
            return ResponseMessageUtility.makeSuccessResponse("OK", HttpStatus.OK);
        else
            return ResponseMessageUtility.makeFailureResponse("Failed to logout from all devices",
                    HttpStatus.ACCEPTED);
    }
}
