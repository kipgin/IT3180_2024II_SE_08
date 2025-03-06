package com.example.BTL_CNPM.service;

import com.example.BTL_CNPM.model.User;

import java.util.Optional;

public interface UserService {
    boolean register(User user);
    boolean login(String username, String password);
    User getUser(String username);
    boolean updateUser(String username, User updatedUser);
    boolean changePassword(String username, String oldPassword, String newPassword);
    boolean setUserActive(String username, boolean isActive);
    boolean deleteUser(String username);
}
