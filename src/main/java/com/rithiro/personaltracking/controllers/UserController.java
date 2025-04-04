package com.rithiro.personaltracking.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rithiro.personaltracking.models.bases.baseResponses.ResponseMessage;
import com.rithiro.personaltracking.models.responses.UserResponse;
import com.rithiro.personaltracking.services.UserService;
import com.rithiro.personaltracking.utils.ResponseMessageUtility;

import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RestController
@RequestMapping("/user")
@Tag(name = "03. User", description = "User controller")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/me")
    public ResponseEntity<ResponseMessage<UserResponse>> getMe() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();
        UserResponse userResponse = userService.findUserByAuthId(jwt.getClaimAsString("sub"));

        return ResponseMessageUtility.makeSuccessResponse(userResponse, "Ok", HttpStatus.OK);
    }
}
