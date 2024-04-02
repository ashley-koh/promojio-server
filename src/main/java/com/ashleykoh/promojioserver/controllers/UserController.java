package com.ashleykoh.promojioserver.controllers;

import com.ashleykoh.promojioserver.controllers.forms.UserDetails;
import com.ashleykoh.promojioserver.exceptions.DuplicateUserException;
import com.ashleykoh.promojioserver.exceptions.NoSuchUserException;
import com.ashleykoh.promojioserver.models.User;
import com.ashleykoh.promojioserver.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.naming.AuthenticationException;

@Validated
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

//     Create a new user
//     pass a user object
//    {
//        "name": "John Doe",
//        "username": "john_doe",
//        "password": "password",
//    }
    @PostMapping
    public User createUser(@RequestBody @Valid User user) throws DuplicateUserException {

        // Check if another user with same username exists
        User duplicate = userRepository.findUsersByUsername(user.getUsername());

        // Throw exception if another user with same username exists
        if (duplicate != null) {
            throw new DuplicateUserException("username is taken");
        }

        // return user obj
        return userRepository.save(user);
    }


    // Single User Operations
    // Get User from id
    @GetMapping("/{id}")
    public User getUser(@PathVariable("id") String id) throws NoSuchUserException {

        // find User using id
        User user = userRepository.findUserById(id);

        // If user exists
        if (user == null) { throw new NoSuchUserException(); }

        // return user obj
        return user;
    }


    // Protected Route
    // Update User name
    @PatchMapping("/{id}/details")
    public User updateUserDetails(
            @PathVariable String id,
            @RequestHeader("username") String username,
            @RequestHeader("password") String password,
            @RequestBody UserDetails userDetails
            ) throws AuthenticationException {
        // get user from database
        User user = userRepository.findUserById(id);

        // check if user exists
        if (user == null) { throw new NoSuchUserException(); }

        // check user credentials
        if (!username.equals(user.getUsername()) || !password.equals(user.getPassword())) { throw new AuthenticationException(); }

        // if checks pass, update name of user
        if (userDetails.getName() != null)  {
            user.setName(userDetails.getName());
        }

        userRepository.save(user);
        return user;
    }

    // Protected Route
    @PatchMapping("/{id}/update/points")
    public User updateUserPoints(
            @PathVariable String id,
            @RequestHeader("username") String username,
            @RequestHeader("password") String password,
            @RequestBody User newUser
    ) throws AuthenticationException {
        User user = userRepository.findUserById(id);

        if (user == null) { throw new NoSuchUserException(); }
        if (!username.equals(user.getUsername()) || !password.equals(user.getPassword())) { throw new AuthenticationException(); }

        user.setPoints(newUser.getPoints());

        userRepository.save(user);
        return user;
    }

    // Protected Route
    @PatchMapping("/{id}/update/tierpoints")
    public User updateUserTierPoints(
            @PathVariable String id,
            @RequestHeader("username") String username,
            @RequestHeader("password") String password,
            @RequestBody User newUser
    ) throws AuthenticationException {
        User user = userRepository.findUserById(id);

        if (user == null) { throw new NoSuchUserException(); }
        if (!username.equals(user.getUsername()) || !password.equals(user.getPassword())) { throw new AuthenticationException(); }

        user.setTierPoints(newUser.getTierPoints());

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
