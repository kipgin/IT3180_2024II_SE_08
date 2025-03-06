//package com.example.BTL_CNPM.controller;
//
//import com.example.BTL_CNPM.model.LoginRequest;
//import com.example.BTL_CNPM.model.User;
//import com.example.BTL_CNPM.service.AuthService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/api/auth")
//public class AuthController {
//
//    private final AuthService authService;
//
//    @Autowired
//    public AuthController(AuthService authService) {
//        this.authService = authService;
//    }
//
//    @PostMapping("/login")
//    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
//        boolean isAuthenticated = authService.login(request);
//        if (isAuthenticated) {
//            return ResponseEntity.ok("Login successful");
//        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<String> register(@RequestBody User user) {
//
//    }
//
//
//}
//
