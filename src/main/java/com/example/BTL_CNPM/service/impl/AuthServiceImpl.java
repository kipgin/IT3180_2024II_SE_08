package com.example.BTL_CNPM.service.impl;

import com.example.BTL_CNPM.model.LoginRequest;
import com.example.BTL_CNPM.model.User;
import com.example.BTL_CNPM.repository.UserRepository;
import com.example.BTL_CNPM.service.AuthService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean login(LoginRequest requestObject) {
        Optional<User> userOptional = userRepository.findByUsername(requestObject.getUsername());
        return userOptional.isPresent() && userOptional.get().getPassword().equals(requestObject.getPassword());
    }

    public boolean register(User user) {

    }
}
