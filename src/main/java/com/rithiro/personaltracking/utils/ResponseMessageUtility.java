package com.rithiro.personaltracking.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.rithiro.personaltracking.models.bases.baseResponses.ResponseHeader;
import com.rithiro.personaltracking.models.bases.baseResponses.ResponseMessage;
import com.rithiro.personaltracking.models.bases.baseResponses.ResponsePagination;

public class ResponseMessageUtility {
    private static <T> ResponseEntity<ResponseMessage<T>> buildResponse(
            boolean result, T body, String message, String errorText, String errorCode,
            ResponsePagination pagination, HttpStatus statusCode) {

        ResponseMessage<T> responseMessage = new ResponseMessage<>();
        ResponseHeader responseHeader = responseMessage.getHeader();

        responseHeader.setResult(result);
        responseHeader.setStatusCode(statusCode.value());
        responseHeader.setMessage(message);
        responseHeader.setErrorText(errorText);
        responseHeader.setErrorCode(errorCode);
        responseHeader.setPagination(pagination);

        if (body != null && !(body instanceof Boolean)) {
            responseMessage.setBody(body);
        }

        return new ResponseEntity<>(responseMessage, statusCode);
    }

    // Success Responses
    public static <T> ResponseEntity<ResponseMessage<T>> makeSuccessResponse(
            T body, ResponsePagination pagination, String message, HttpStatus statusCode) {
        return buildResponse(true, body, message, null, null, pagination, statusCode);
    }

    public static <T> ResponseEntity<ResponseMessage<T>> makeSuccessResponse(
            T body, String message, HttpStatus statusCode) {
        return buildResponse(true, body, message, null, null, null, statusCode);
    }

    public static <T> ResponseEntity<ResponseMessage<T>> makeSuccessResponse(
            String message, HttpStatus statusCode) {
        return buildResponse(true, null, message, null, null, null, statusCode);
    }

    // Failure Responses
    public static <T> ResponseEntity<ResponseMessage<T>> makeFailureResponse(
            T body, String errorText, String errorCode, HttpStatus statusCode) {
        return buildResponse(false, body, null, errorText, errorCode, null, statusCode);
    }

    public static <T> ResponseEntity<ResponseMessage<T>> makeFailureResponse(
            T body, String errorText, HttpStatus statusCode) {
        return buildResponse(false, body, null, errorText, null, null, statusCode);
    }

    public static <T> ResponseEntity<ResponseMessage<T>> makeFailureResponse(
            String errorText, String errorCode, HttpStatus statusCode) {
        return buildResponse(false, null, null, errorText, errorCode, null, statusCode);
    }

    public static <T> ResponseEntity<ResponseMessage<T>> makeFailureResponse(
            String errorText, HttpStatus statusCode) {
        return buildResponse(false, null, null, errorText, null, null, statusCode);
    }

    // Unauthorized Responses
    public static <T> ResponseEntity<ResponseMessage<T>> makeUnauthorizedResponse(
            String message, String errorText) {
        return buildResponse(false, null, message, errorText, null, null, HttpStatus.UNAUTHORIZED);
    }

    public static <T> ResponseEntity<ResponseMessage<T>> makeUnauthorizedResponse(
            String errorText) {
        return buildResponse(false, null, null, errorText, null, null, HttpStatus.UNAUTHORIZED);
    }
}
