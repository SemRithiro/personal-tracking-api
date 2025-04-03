package com.rithiro.personaltracking.modules.calendar.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rithiro.personaltracking.models.bases.baseResponses.ResponseMessage;
import com.rithiro.personaltracking.utils.ResponseMessageUtility;

import io.swagger.v3.oas.annotations.tags.Tag;

@Validated
@RestController
@RequestMapping("/calendar")
@Tag(name = "03. Calendar", description = "Calendar controller")
public class calendarController {

    @PostMapping("/create")
    public ResponseEntity<ResponseMessage<String>> create() {
        return ResponseMessageUtility.makeSuccessResponse("Success", HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ResponseMessage<String>> getList() {
        return ResponseMessageUtility.makeSuccessResponse("Success", HttpStatus.OK);
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
