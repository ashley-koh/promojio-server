package com.ashleykoh.promojioserver.controllers;

import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class BasicController {

    // Following Response templates follow JSend Response Format
    // https://github.com/omniti-labs/jsend
    static ResponseEntity<Map<String, Object>> successResponse(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("data", data);

        return ResponseEntity.ok().body(response);
    }

    static ResponseEntity<Map<String, Object>> failResponse(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "fail");
        response.put("data", data);

        return ResponseEntity.badRequest().body(response);
    }

    static ResponseEntity<Map<String, String>> errorResponse(String message) {
        Map<String, String> response = new HashMap<>();
        response.put("status", "error");
        response.put("data", message);

        return ResponseEntity.badRequest().body(response);
    }

    static ResponseEntity<Map<String, Object>> userDoesNotExistResponse() {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "no such user with given id");

        return failResponse(data);
    }


    static ResponseEntity<Map<String, Object>> invalidCredentialsResponse() {
        Map<String, Object> data = new HashMap<>();
        data.put("credentials", "invalid");

        return failResponse(data);
    }
}
