package com.example.BTL_CNPM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BTL_CNPM.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}