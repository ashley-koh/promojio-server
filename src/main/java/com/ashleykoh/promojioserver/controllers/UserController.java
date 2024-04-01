package com.ashleykoh.promojioserver.controllers;

import com.ashleykoh.promojioserver.models.User;
import com.ashleykoh.promojioserver.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {
    private static byte[] getSHA(String input) throws NoSuchAlgorithmException
    {
        // Static getInstance method is called with hashing SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");

        // digest() method called
        // to calculate message digest of an input
        // and return array of byte
        return md.digest(input.getBytes(StandardCharsets.UTF_8));
    }

    private static String toHexString(byte[] hash)
    {
        // Convert byte array into signum representation
        BigInteger number = new BigInteger(1, hash);

        // Convert message digest into hex value
        StringBuilder hexString = new StringBuilder(number.toString(16));

        // Pad with leading zeros
        while (hexString.length() < 64)
        {
            hexString.insert(0, '0');
        }

        return hexString.toString();
    }

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) {
        User user = userRepository.findUserById(id);
        System.out.println(user);
        return user;
    }

    @PostMapping
    public ResponseEntity<String> createUser(@RequestBody @Valid User user) {
        try {
            // Check if another user with same username exists
            User duplicate = userRepository.findUsersByUsername(user.getUsername());

            if (duplicate != null) {
                return ResponseEntity.badRequest().body("username is taken");
            }

            // Hash password
            user.setPassword(toHexString(getSHA(user.getPassword())));
            userRepository.save(user);
            return ResponseEntity.ok("User created successfully");

        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Exception thrown for incorrect algorithm: " + ex);
            return ResponseEntity.badRequest().body("Server Error");
        }
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
    }

}
