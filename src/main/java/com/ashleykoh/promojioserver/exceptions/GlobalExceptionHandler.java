package com.ashleykoh.promojioserver.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.naming.AuthenticationException;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    // Invalid input arguments
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        for (ObjectError error : ex.getAllErrors()) {
            errorMap.put(error.getObjectName(), error.getDefaultMessage());
        }

        return ResponseEntity.badRequest().body(errorMap);
    }

    // Invalid Credentials
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleValidationException(AuthenticationException ex) {

        return ResponseEntity.badRequest().body("invalid credentials");
    }

    // If duplicate user exists
    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateUserException(DuplicateUserException ex) {
        Map<String, String> errorMap = new HashMap<>();
        if (ex.getMessage() != null) {
            errorMap.put("error", ex.getMessage());
        } else {
            errorMap.put("error", "duplicate user exists");
        }
        return ResponseEntity.badRequest().body(errorMap);
    }

    // No user found
    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<String> handleValidationException(NoSuchUserException ex) {

        return ResponseEntity.badRequest().body("No such user exists");
    }

}
