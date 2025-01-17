package com.ecommerce.userservice.controller;

import com.ecommerce.userservice.model.User;
import com.ecommerce.userservice.repository.UserRepository;
import com.ecommerce.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Registration Endpoint
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    // Authentication Endpoint
    @PostMapping("/authenticate")
    public ResponseEntity<User> authenticateUser(@RequestParam String email, @RequestParam String password) {
        User authenticatedUser = userService.authenticateUser(email, password);
        return ResponseEntity.ok(authenticatedUser);
    }

    // Get Profile Endpoint
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long id) {
        User user = userService.getUserProfile(id);
        return ResponseEntity.ok(user);
    }

    // Update Profile Endpoint
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody User updatedUser) {
        User user = userService.updateUserProfile(id, updatedUser);
        return ResponseEntity.ok(user);
    }

    // Delete User Account Endpoint
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}