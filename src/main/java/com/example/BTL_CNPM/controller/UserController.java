package com.example.BTL_CNPM.controller;

import com.example.BTL_CNPM.model.LoginRequest;
import com.example.BTL_CNPM.model.PasswordChangeRequest;
import com.example.BTL_CNPM.model.SetActiveStatusRequest;
import com.example.BTL_CNPM.model.User;
import com.example.BTL_CNPM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

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
    public boolean deleteUser(@PathVariable String username) {
        return userService.deleteUserByUsername(username);
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
}
