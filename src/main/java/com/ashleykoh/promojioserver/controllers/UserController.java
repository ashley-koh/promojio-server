package com.ashleykoh.promojioserver.controllers;

import com.ashleykoh.promojioserver.exceptions.NoSuchUserException;
import com.ashleykoh.promojioserver.models.User;
import com.ashleykoh.promojioserver.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

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

//     Create a new user
//     pass a user object
//    {
//        "name": "John Doe",
//        "username": "john_doe",
//        "password": "password",
//    }
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


    // Single User Operations

    // Get User from id
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) throws NoSuchUserException {

        User user = userRepository.findUserById(id);

        if (user == null) { throw new NoSuchUserException(); }

        return user;
    }


    // Update User details such as name and username
    @PatchMapping("/{id}/details")
    public User updateUserDetails(@PathVariable String id, @RequestBody @Valid User newUser) throws AuthenticationException {
        User user = userRepository.findUserById(id);

        if (user == null) { throw new NoSuchUserException(); }
        if (!newUser.getPassword().equals(user.getPassword())) { throw new AuthenticationException(); }


        if (newUser.getName() != null)  {
            user.setName(newUser.getName());
        }

        if (newUser.getUsername() != null) {
            user.setUsername(newUser.getUsername());
        }

        User updatedUser = userRepository.save(user);
//        System.out.println(updatedUser);
        return user;
    }

    @PatchMapping("/{id}/update/points")
    public User updateUserPoints(@PathVariable String id, @RequestBody User newUser) throws AuthenticationException {
        User user = userRepository.findUserById(id);

        if (user == null) { throw new NoSuchUserException(); }
        if (!newUser.getPassword().equals(user.getPassword())) { throw new AuthenticationException(); }

        System.out.println(user);

        user.setPoints(newUser.getPoints());

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
