package com.example.BTL_CNPM.controller;

import com.example.BTL_CNPM.model.LoginRequest;
import com.example.BTL_CNPM.model.PasswordChangeRequest;
import com.example.BTL_CNPM.model.User;
import com.example.BTL_CNPM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public interface UserController {
    @PostMapping("/register")
    ResponseEntity<String> register(@RequestBody User user);

    @PostMapping("/login")
    ResponseEntity<String> login(@RequestBody LoginRequest request);

    @GetMapping("/{username}")
    ResponseEntity<User> getUser(@PathVariable String username);

    @PutMapping("/{username}")
    ResponseEntity<String> updateUser(@PathVariable String username, @RequestBody User updatedUser);

    @PutMapping("/{username}/password")
    ResponseEntity<String> changePassword(@PathVariable String username, @RequestBody PasswordChangeRequest request);

    @PatchMapping("/{username}/activate")
    ResponseEntity<String> activateUser(@PathVariable String username);

    @PatchMapping("/{username}/deactivate")
    ResponseEntity<String> deactivateUser(@PathVariable String username);

    @DeleteMapping("/{username}")
    ResponseEntity<String> deleteUser(@PathVariable String username);
}
