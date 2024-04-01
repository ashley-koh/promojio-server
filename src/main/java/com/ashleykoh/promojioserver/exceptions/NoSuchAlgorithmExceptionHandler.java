package com.ashleykoh.promojioserver.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.NoSuchAlgorithmException;

public class NoSuchAlgorithmExceptionHandler {

    @ExceptionHandler(NoSuchAlgorithmException.class)
    public ResponseEntity<String> handleValidationException(NoSuchAlgorithmException ex) {
        return ResponseEntity.badRequest().body("Hashing Error");
    }
}
