package com.rithiro.personaltracking.models.bases.baseResponses;

import lombok.Data;

@Data
public class ResponseMessage<T> {
    private ResponseHeader header;
    private T body;
    private String poweredBy = "UDAYA-Technology 2025";

    public ResponseHeader getHeader() {
        if (this.header == null)
            this.header = new ResponseHeader();
        return this.header;
    }
}
