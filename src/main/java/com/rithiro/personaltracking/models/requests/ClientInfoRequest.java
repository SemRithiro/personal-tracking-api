package com.rithiro.personaltracking.models.requests;

import lombok.Data;

@Data
public class ClientInfoRequest {
    private String method;
    private String requestURI;
    private String remoteAddr;
    private String userAgent;
}
