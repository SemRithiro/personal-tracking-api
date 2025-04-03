package com.rithiro.personaltracking.controllers;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/")
@Tag(name = "01. Welcome", description = "Welcome screen")
public class WelcomeController {
    @Value("${spring.application.name}")
    String APPLICATION_NAME;

    @Value("${info.app.version}")
    String APPLICATION_VERSION;

    @GetMapping
    public String welcome() {
        return ("<span style=\"font-family: monospace;\">It's worked.</span>" +
                "</br>" +
                "<span style=\"font-family: monospace;\">Project: %s V%s</span>" +
                "</br>" +
                "<span style=\"font-family: monospace;\">%s</span>").formatted(
                        APPLICATION_NAME,
                        APPLICATION_VERSION,
                        new Date());
    }
}
