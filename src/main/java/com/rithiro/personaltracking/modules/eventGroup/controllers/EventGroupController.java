package com.rithiro.personaltracking.modules.eventGroup.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rithiro.personaltracking.models.bases.baseResponses.ResponseMessage;
import com.rithiro.personaltracking.models.responses.UserResponse;
import com.rithiro.personaltracking.modules.eventGroup.models.responses.EventGroupResponse;
import com.rithiro.personaltracking.modules.eventGroup.services.EventGroupService;
import com.rithiro.personaltracking.services.UserService;
import com.rithiro.personaltracking.utils.ResponseMessageUtility;

import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RestController
@RequestMapping("/event-group")
@Tag(name = "04. Event Group", description = "Event Group controller")
public class EventGroupController {

    @Autowired
    UserService userService;

    @Autowired
    EventGroupService eventGroupService;

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage<String>> create() {
        return ResponseMessageUtility.makeSuccessResponse("Success", HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseMessage<List<EventGroupResponse>>> getList() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Jwt jwt = (Jwt) authentication.getCredentials();
        UserResponse userResponse = userService.findUserByAuthId(jwt.getClaimAsString("sub"));
        return ResponseMessageUtility.makeSuccessResponse(eventGroupService.getList(userResponse.getId()), "Success",
                HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<ResponseMessage<String>> getListWithFilter() {
        return ResponseMessageUtility.makeSuccessResponse("Success", HttpStatus.OK);
    }

    @PatchMapping("/update")
    public ResponseEntity<ResponseMessage<String>> update() {
        return ResponseMessageUtility.makeSuccessResponse("Success", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ResponseMessage<String>> delete() {
        return ResponseMessageUtility.makeSuccessResponse("Success", HttpStatus.OK);
    }
}
