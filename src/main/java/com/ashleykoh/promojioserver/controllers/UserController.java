package com.ashleykoh.promojioserver.controllers;

import com.ashleykoh.promojioserver.controllers.forms.UserDetails;
import com.ashleykoh.promojioserver.controllers.forms.UserPoints;
import com.ashleykoh.promojioserver.controllers.forms.UserTierPoints;
import com.ashleykoh.promojioserver.models.User;
import com.ashleykoh.promojioserver.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserRepository userRepository;

//     Create a new user
//     pass a user object
//    {
//        "name": "John Doe",
//        "username": "john_doe",
//        "password": "password",
//    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> createUser(@RequestBody @Valid User user) {

        // Check if another user with same username exists
        User duplicate = userRepository.findUsersByUsername(user.getUsername());

        // Throw exception if another user with same username exists
        if (duplicate != null) {
            Map<String, String> data = new HashMap<>();
            data.put("username", "username is taken");

            return failResponse(data);
        }

        // save user obj
        userRepository.save(user);
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);

        return successResponse(data);
    }



    // Single User Operations
    // Get User from id
    @GetMapping("/{id}")
    public ResponseEntity<Map<String, Object>> getUser(@PathVariable("id") String id) {
        // find User using id
        User user = userRepository.findUserById(id);

        // If user exists
        if (user == null) {
            Map<String, String> data = new HashMap<>();
            data.put("id", "no such user with given id");
            return failResponse(data);
        }

        // return user obj
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        return successResponse(data);
    }


    // Protected Route
    // Update User name
    @PatchMapping("/{id}/update/details")
    public ResponseEntity<Map<String, Object>> updateUserDetails(
            @PathVariable String id,
            @RequestHeader("username") String username,
            @RequestHeader("password") String password,
            @RequestBody UserDetails userDetails
    ) {
        // get user from database
        User user = userRepository.findUserById(id);

        // check if user does not exist
        if (user == null) { return userDoesNotExistResponse(); }

        // check user credentials
        if (!username.equals(user.getUsername()) || !password.equals(user.getPassword())) {
            return invalidCredentialsResponse();
        }

        // if checks pass, update name of user
        if (userDetails.getName() != null)  {
            user.setName(userDetails.getName());
        }

        userRepository.save(user);
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        return successResponse(data);
    }

    // Protected Route
    @PatchMapping("/{id}/update/points")
    public ResponseEntity<Map<String, Object>> updateUserPoints(
            @PathVariable String id,
            @RequestHeader("username") String username,
            @RequestHeader("password") String password,
            @RequestBody UserPoints userPoints
    ) {
        User user = userRepository.findUserById(id);

        // check if user does not exist
        if (user == null) { return userDoesNotExistResponse(); }

        // check user credentials
        if (!username.equals(user.getUsername()) || !password.equals(user.getPassword())) {
            return invalidCredentialsResponse();
        }

        user.setPoints(userPoints.getPoints());

        userRepository.save(user);
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        return successResponse(data);
    }

    // Protected Route
    @PatchMapping("/{id}/update/tierpoints")
    public ResponseEntity<Map<String, Object>> updateUserTierPoints(
            @PathVariable String id,
            @RequestHeader("username") String username,
            @RequestHeader("password") String password,
            @RequestBody UserTierPoints userTierPoints
    ) {
        User user = userRepository.findUserById(id);

        // check if user does not exist
        if (user == null) { return userDoesNotExistResponse(); }

        // check user credentials
        if (!username.equals(user.getUsername()) || !password.equals(user.getPassword())) {
            return invalidCredentialsResponse();
        }

        int tierPoints = userTierPoints.getTierPoints();

        // if tierPoints less than 0 return failed request
        if (tierPoints < 0) {
            Map<String, String> data = new HashMap<>();
            data.put("tierPoints", "tierPoints must be 0 and above");
            return failResponse(data);
        }

        // set user tier points
        user.setTierPoints(tierPoints);

        // set user member tier based on tier points
        if (tierPoints < 1000) {
            user.setMemberTier("bronze");
        } else if (tierPoints < 2000) {
            user.setMemberTier("silver");
        } else if (tierPoints < 3000) {
            user.setMemberTier("gold");
        } else {
            user.setMemberTier("platinum");
        }

        userRepository.save(user);
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);
        return successResponse(data);
    }

    // Important Behavior: Can be called successfully multiple times in a row
    // It guarantees no such document with user id exists
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return successResponse(null);
    }

}
