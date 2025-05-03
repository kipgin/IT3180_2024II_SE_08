package com.example.BTL_CNPM.gmail.service;

import com.example.BTL_CNPM.gmail.model.users.UsersGmail;
import com.example.BTL_CNPM.gmail.repository.UsersGmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsersGmailService {
    @Autowired
    private UsersGmailRepository usersGmailRepository;

    public UsersGmail saveUser(UsersGmail user) {
        return usersGmailRepository.save(user);
    }

    public Optional<UsersGmail> getUserByUsername(String username) {
        return usersGmailRepository.findById(username);
    }

    public Optional<UsersGmail> getUserByEmail(String email) {
        return usersGmailRepository.findByEmail(email);
    }

    public void deleteUser(String username) {
        usersGmailRepository.deleteById(username);
    }

    public List<UsersGmail> getAllUsers() {
        return usersGmailRepository.findAll();
    }

    public Optional<UsersGmail> findByUsername(String username) {
        return usersGmailRepository.findById(username);
    }
}
