package com.example.BTL_CNPM.service;

import com.example.BTL_CNPM.model.LoginRequest;
import com.example.BTL_CNPM.model.User;

public interface AuthService {
    boolean login(LoginRequest requestObject);
    boolean register(User user);
    boolean updateUserInfo(String username, User updateUser);
    boolean changePassword(String username, String oldPassword, String newPassword);
    boolean setActiveStatus(String username, boolean isActive);
}
