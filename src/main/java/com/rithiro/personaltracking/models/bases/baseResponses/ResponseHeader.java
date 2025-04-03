package com.rithiro.personaltracking.models.bases.baseResponses;

import java.util.Date;

import lombok.Data;

@Data
public class ResponseHeader {
    private Long serverTimeStamp;
    private Boolean result;
    private Integer statusCode;
    private String errorCode;
    private String errorText;
    private String message;
    private ResponsePagination pagination;

    public ResponseHeader() {
        this.serverTimeStamp = new Date().getTime();
    }
}
