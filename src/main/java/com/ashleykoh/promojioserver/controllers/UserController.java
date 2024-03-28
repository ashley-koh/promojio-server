package com.ashleykoh.promojioserver.controllers;

import com.ashleykoh.promojioserver.models.User;
import com.ashleykoh.promojioserver.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userRepository.findUserById(id);
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid User user) {

        User duplicate = userRepository.findUsersByUsername(user.getUsername());

        if (duplicate != null) {
            return ResponseEntity.badRequest().body("username is taken");
        }

        userRepository.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }

}
