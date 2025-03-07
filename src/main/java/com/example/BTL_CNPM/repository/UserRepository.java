package com.example.BTL_CNPM.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BTL_CNPM.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
}