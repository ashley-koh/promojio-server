package com.ashleykoh.promojioserver.controllers;

import com.ashleykoh.promojioserver.exceptions.ServerRuntimeException;
import com.ashleykoh.promojioserver.models.User;
import com.ashleykoh.promojioserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class BaseController {

    @Autowired
    UserRepository userRepository;

     protected void validateUser(String id, String username, String password) {
        User user = userRepository.findUserById(id);

        // check if user does not exist
        if (user == null) { throw new ServerRuntimeException("user", "user does not exist"); }

        // check user credentials
        if (!username.equals(user.getUsername()) || !password.equals(user.getPassword())) {
            throw new ServerRuntimeException("credentials", "invalid");
        }

    }

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
}
