package com.ashleykoh.promojioserver.controllers;

import com.ashleykoh.promojioserver.controllers.forms.Login;
import com.ashleykoh.promojioserver.controllers.forms.UserDetails;
import com.ashleykoh.promojioserver.controllers.forms.UserPoints;
import com.ashleykoh.promojioserver.controllers.forms.UserTierPoints;
import com.ashleykoh.promojioserver.exceptions.ServerRuntimeException;
import com.ashleykoh.promojioserver.models.User;
import com.ashleykoh.promojioserver.repositories.CustomUserRepository;
import com.ashleykoh.promojioserver.repositories.CustomUserRepositoryImpl;
import com.ashleykoh.promojioserver.repositories.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Validated
@RestController
@RequestMapping("/user")
public class UserController extends BaseController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserRepository customUserRepository;

    private User validateUser(String id, String username, String password) {
        User user = userRepository.findUserById(id);

        // check if user does not exist
        if (user == null) { throw new ServerRuntimeException("user", "user does not exist"); }

        // check user credentials
        if (!username.equals(user.getUsername()) || !password.equals(user.getPassword())) {
            throw new ServerRuntimeException("credentials", "invalid");
        }

        return user;
    }

    @GetMapping("/leaderboard")
    public ResponseEntity<Map<String, Object>> getTierPointsLeaderboard() {
        List<User> users = customUserRepository.getLeaderboard();

        Map<String, Object> data = new HashMap<>();
        data.put("leaderboard", users);

        return successResponse(data);
    }

//     Create a new user
//     pass a user object
//    {
//        "name": "John Doe",
//        "username": "john_doe",
//        "password": "password",
//    }
    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody @Valid User user) {

        // Check if another user with same username exists
        User duplicate = userRepository.findUsersByUsername(user.getUsername());

        // Throw exception if another user with same username exists
        if (duplicate != null) {
            throw new ServerRuntimeException("username", "username is taken");
        }

        // save user obj
        userRepository.save(user);
        Map<String, Object> data = new HashMap<>();
        data.put("user", user);

        return successResponse(data);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> loginUser(@RequestBody Login login) {

        String username = login.getUsername();
        String password = login.getPassword();

        User user = userRepository.findUsersByUsername(username);

        // if user with username does not exist or password is wrong
        if (user == null || !user.getPassword().equals(password)) {
            throw new ServerRuntimeException("credentials", "invalid");
        }

        // create data obj with user
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
            throw new ServerRuntimeException("id", "no such user with given id");
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
        validateUser(id, username, password);

        customUserRepository.updateUserName(id, userDetails.getName());

        Map<String, Object> data = new HashMap<>();
        data.put("updated", true);
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
        validateUser(id, username, password);

        customUserRepository.updateUserPoints(id, userPoints.getPoints());

        Map<String, Object> data = new HashMap<>();
        data.put("updated", true);
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
        validateUser(id, username, password);

        customUserRepository.updateUserTierPoints(id, userTierPoints.getTierPoints());

        Map<String, Object> data = new HashMap<>();
        data.put("updated", true);
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
