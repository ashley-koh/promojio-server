package com.ashleykoh.promojioserver.controllers;

import com.ashleykoh.promojioserver.models.User;
import com.ashleykoh.promojioserver.repositories.UserRepository;
import com.mongodb.client.MongoClients;
import jakarta.validation.Valid;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

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

    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) {

        // Add no such user exception

        return userRepository.findUserById(id);
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
            return ResponseEntity.ok(user.getId());

        } catch (NoSuchAlgorithmException ex) {
            System.out.println("Exception thrown for incorrect algorithm: " + ex);
            return ResponseEntity.badRequest().body("Server Error");
        }
    }

    // Update User details such as name and username
    @PutMapping("/{id}")
    public User updateUser(@PathVariable String id, @RequestBody @Valid User newUser) {
        User user = userRepository.findUserById(id);

        // Add no such user exception

        System.out.println(user);

        if (newUser.getName() != null)  {
            user.setName(newUser.getName());
        }

        if (newUser.getUsername() != null) {
            user.setUsername(newUser.getUsername());
        }

        // Hash password
        userRepository.save(user);
        return user;
    }

    // Important Behavior: Can be called successfully multiple times in a row
    // It guarantees no such document with user id exists
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok("id no longer exists");
    }

}
