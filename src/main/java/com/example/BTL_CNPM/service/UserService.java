package com.example.BTL_CNPM.service;

import com.example.BTL_CNPM.model.Household;
import com.example.BTL_CNPM.model.User;
import com.example.BTL_CNPM.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HouseholdService householdService;

    public boolean register(User user) {
        if (userRepository.existsById(user.getUsername())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public boolean login(String username, String password) {
        Optional<User> userOpt = userRepository.findById(username);
        return userOpt.filter(user -> user.getPassword().equals(password)).isPresent();
    }

    public boolean updateUserInfo(String username, User updatedUser) {
        if (userRepository.existsById(username)) {
            updatedUser.setUsername(username);
            userRepository.save(updatedUser);
            return true;
        }
        return false;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) {
        Optional<User> userOpt = userRepository.findById(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(oldPassword)) {
            User user = userOpt.get();
            user.setPassword(newPassword);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    public boolean setActiveStatus(String username, boolean isActive) {
        Optional<User> userOpt = userRepository.findById(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            user.setActive(isActive);
            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean deleteUserByUsername(String username) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent()) {
            householdService.deleteHouseholdByUsername(username);
            userRepository.deleteByUsername(username);
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}

