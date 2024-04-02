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
    public ResponseEntity<Map<String, Object>> handleValidationException(MethodArgumentNotValidException ex) {
        Map<String, Object> data = new HashMap<>();
        for (ObjectError error : ex.getAllErrors()) {
            data.put(error.getObjectName(), error.getDefaultMessage());
        }

        Map<String, Object> failMap = new HashMap<>();
        failMap.put("status", "fail");
        failMap.put("data", data);

        return ResponseEntity.badRequest().body(failMap);
    }

    @ExceptionHandler(ServerRuntimeException.class)
    public ResponseEntity<Map<String, Object>> handleServerRuntimeException(ServerRuntimeException ex) {
        Map<String, Object> data = new HashMap<>();
        data.put(ex.getLabel(), ex.getMessage());

        Map<String, Object> failMap = new HashMap<>();
        failMap.put("status", "fail");
        failMap.put("data", data);

        return ResponseEntity.badRequest().body(failMap);
    }
}
