package com.rithiro.personaltracking.controllers;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.spring.MyBatisSystemException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rithiro.personaltracking.models.bases.baseResponses.ResponseMessage;
import com.rithiro.personaltracking.utils.ResponseMessageUtility;

@RestControllerAdvice
public class ValidationException {
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ResponseMessage<String>> handleRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException ex) {
        return ResponseMessageUtility.makeFailureResponse(ex.getMessage(), HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseMessage<HashMap<String, String>>> handleValidationEx(
            MethodArgumentNotValidException ex) {
        HashMap<String, String> errors = new HashMap<>();
        ex.getFieldErrors().forEach(fieldError -> {
            errors.put(fieldError.getField(), fieldError.getDefaultMessage());
        });

        return ResponseMessageUtility.makeFailureResponse(errors, "Bad Request", HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseMessage<List<String>>> handleRunTimeException(
            RuntimeException ex) {
        return ResponseMessageUtility.makeFailureResponse(ex.getLocalizedMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MyBatisSystemException.class)
    public ResponseEntity<ResponseMessage<List<String>>> handleMybatisException(
            MyBatisSystemException ex) {
        return ResponseMessageUtility.makeFailureResponse(ex.getMostSpecificCause().getMessage(),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(PersistenceException.class)
    public ResponseEntity<ResponseMessage<String>> handlePersistenceException(PersistenceException ex) {
        return ResponseMessageUtility.makeFailureResponse("Database error occured: " + ex.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadSqlGrammarException.class)
    public ResponseEntity<ResponseMessage<String>> handleBadSqlException(BadSqlGrammarException ex) {
        return ResponseMessageUtility.makeFailureResponse("SQL Syntax Error: " + ex.getMostSpecificCause(),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<ResponseMessage<String>> handleDuplicateKeyException(DuplicateKeyException ex) {
        return ResponseMessageUtility.makeFailureResponse("Duplicate entry: " + ex.getMostSpecificCause(),
                HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseMessage<String>> handleDataIntegrityException(DataIntegrityViolationException ex) {
        return ResponseMessageUtility.makeFailureResponse("Data Integrity error: " + ex.getMostSpecificCause(),
                HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ResponseMessage<String>> handleUnauthorizedException(AuthenticationException ex) {
        return ResponseMessageUtility.makeUnauthorizedResponse("UNAUTHORIZED", ex.getMessage());
    }
}
