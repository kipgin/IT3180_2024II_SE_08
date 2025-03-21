package com.example.BTL_CNPM.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.BTL_CNPM.user.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);
}