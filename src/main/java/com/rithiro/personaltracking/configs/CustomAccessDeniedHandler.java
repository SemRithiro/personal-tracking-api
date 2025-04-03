package com.rithiro.personaltracking.configs;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException {
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");
        PrintWriter writer = response.getWriter();
        writer.write(
                "{\"error\": \"Forbidden\", \"message\": \"You do not have permission to access this resource.\"}");
        writer.flush();
    }
}
