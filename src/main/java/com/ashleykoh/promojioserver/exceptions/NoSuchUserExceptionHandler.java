package com.ashleykoh.promojioserver.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class NoSuchUserExceptionHandler {
    @ExceptionHandler(NoSuchUserException.class)
    public ResponseEntity<String> handleValidationException(NoSuchUserException ex) {

        return ResponseEntity.badRequest().body("No such user exists");
    }
}
