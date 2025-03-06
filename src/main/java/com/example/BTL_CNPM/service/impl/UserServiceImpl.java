package com.example.BTL_CNPM.service.impl;
import com.example.BTL_CNPM.model.User;
import com.example.BTL_CNPM.repository.UserRepository;
import com.example.BTL_CNPM.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @Override
    public boolean register(User user) {
        return false;
    }

    @Override
    public boolean login(String username, String password) {
        return false;
    }

    @Override
    public User getUser(String username) {
        return null;
    }

    @Override
    public boolean updateUser(String username, User updatedUser) {
        return false;
    }

    @Override
    public boolean changePassword(String username, String oldPassword, String newPassword) {
        return false;
    }

    @Override
    public boolean setUserActive(String username, boolean isActive) {
        return false;
    }

    @Override
    public boolean deleteUser(String username) {
        return false;
    }
}
