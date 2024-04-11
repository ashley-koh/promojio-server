package com.ashleykoh.promojioserver.controllers;

import com.ashleykoh.promojioserver.controllers.forms.*;
import com.ashleykoh.promojioserver.exceptions.ServerRuntimeException;
import com.ashleykoh.promojioserver.models.Promo;
import com.ashleykoh.promojioserver.models.User;
import com.ashleykoh.promojioserver.repositories.CustomUserRepository;
import com.ashleykoh.promojioserver.repositories.PromoRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
    private CustomUserRepository customUserRepository;

    @Autowired
    private PromoRepository promoRepository;

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
    public ResponseEntity<Map<String, Object>> getUser(
            @PathVariable("id") String id,
            @RequestHeader("username") String username,
            @RequestHeader("password") String password
    ) {
        validateUser(id, username, password);
        // find User using id
        User user = userRepository.findUserById(id);

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

        customUserRepository.incrementUserPoints(id, userPoints.getPoints());

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

        customUserRepository.incrementUserTierPoints(id, userTierPoints.getTierPoints());

        Map<String, Object> data = new HashMap<>();
        data.put("updated", true);
        return successResponse(data);
    }

    @PostMapping("/{id}/add/promo")
    public ResponseEntity<Map<String, Object>> addPromoToUserPromos(
            @PathVariable String id,
            @RequestHeader("username") String username,
            @RequestHeader("password") String password,
            @RequestBody AddPromoToUserForm promoToUserForm
            ) {
        validateUser(id, username, password);

        customUserRepository.addPromoToUser(id, promoToUserForm.getPromoId());

        Map<String, Object> data = new HashMap<>();
        data.put("updated", true);
        return successResponse(data);
    }

    @PostMapping("/{id}/create/promo")
    public ResponseEntity<Map<String, Object>> userCreatePromo(
            @PathVariable String id,
            @RequestHeader("username") String username,
            @RequestHeader("password") String password,
            @RequestBody Promo promo
    ) {
        validateUser(id, username, password);

        // add promo to database
        Promo newPromo = promoRepository.save(promo);

        // add promo to user
        customUserRepository.addPromoToUser(id, newPromo.getId());

        // give user tierPoints for submitting promo codes
        customUserRepository.incrementUserTierPoints(id, 200);

        Map<String, Object> data = new HashMap<>();
        data.put("updated", true);
        return successResponse(data);
    }

    @PostMapping("/{user_id}/use/promo/{promo_id}")
    public ResponseEntity<Map<String, Object>> usePromo(
            @PathVariable String user_id,
            @PathVariable String promo_id,
            @RequestHeader("username") String username,
            @RequestHeader("password") String password
    ) {
        validateUser(user_id, username, password);

        customUserRepository.usePromo(user_id, promo_id);

        Map<String, Object> data = new HashMap<>();
        data.put("updated", true);
        return successResponse(data);
    }


    // Important Behavior: Can be called successfully multiple times in a row
    // It guarantees no such document with user id exists
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> deleteUser(
            @PathVariable String id,
            @RequestHeader("username") String username,
            @RequestHeader("password") String password
    ) {
        validateUser(id, username, password);
        userRepository.deleteById(id);
        return successResponse(null);
    }

}
