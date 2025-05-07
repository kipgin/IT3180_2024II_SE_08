package com.example.BTL_CNPM.user.controller;

import com.example.BTL_CNPM.user.model.LoginRequest;
import com.example.BTL_CNPM.user.model.PasswordChangeRequest;
import com.example.BTL_CNPM.user.model.SetActiveStatusRequest;
import com.example.BTL_CNPM.user.model.User;
import com.example.BTL_CNPM.user.service.PasswordResetService;
import com.example.BTL_CNPM.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordResetService passwordResetService;

    @PostMapping("/register")
    public boolean register(@RequestBody User user) {
        return userService.register(user);
    }

    @PostMapping("/login")
    public boolean login(@RequestBody LoginRequest loginRequest) {
        return userService.login(loginRequest.getUsername(), loginRequest.getPassword());
    }

    @PutMapping("/update")
    public boolean updateUserInfo(@RequestBody User updatedUser) {
        return userService.updateUserInfo(updatedUser.getUsername(), updatedUser);
    }

    @PutMapping("/change-password")
    public boolean changePassword(@RequestBody PasswordChangeRequest passwordChangeRequest) {
        return userService.changePassword(passwordChangeRequest.getUsername(), passwordChangeRequest.getOldPassword(), passwordChangeRequest.getNewPassword());
    }

    @PutMapping("/set-active")
    public boolean setActiveStatus(@RequestBody SetActiveStatusRequest setActiveStatusRequest) {
        return userService.setActiveStatus(setActiveStatusRequest.getUsername(), setActiveStatusRequest.isActive());
    }

    @DeleteMapping("/delete/{username}")
    public boolean deleteUserBy(@PathVariable String username) {
        return userService.deleteUserByUsername(username);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/get/{username}")
    public Optional<User> getUser(@PathVariable String username){
        return userService.getUser(username);
    }

    // Endpoint gửi mã reset mật khẩu
    @PostMapping("/forgot/{username}")
    public ResponseEntity<String> sendPasswordResetToken(@PathVariable String username) {
        boolean result = userService.sendPasswordResetToken(username);

        if (result) {
            return ResponseEntity.ok("Password reset link sent successfully to your email.");
        } else {
            return ResponseEntity.status(400).body("Failed to send password reset link. Please check your username or email.");
        }
    }

    // Endpoint xác minh token
    @GetMapping("/verify-token/{username}/{token}")
    public ResponseEntity<String> verifyToken(
            @PathVariable String token,
            @PathVariable String username) {

        // Gọi phương thức verifyToken từ service
        boolean isTokenValid = passwordResetService.verifyToken(token, username);

        if (isTokenValid) {
            return ResponseEntity.ok("Token is valid.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Invalid or expired token.");
        }
    }
}
