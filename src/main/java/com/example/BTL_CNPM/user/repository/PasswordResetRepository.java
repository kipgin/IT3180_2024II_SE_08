package com.example.BTL_CNPM.user.repository;

import com.example.BTL_CNPM.user.model.PasswordReset;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PasswordResetRepository extends JpaRepository<PasswordReset, String> {

    Optional<PasswordReset> findByUsername(String username);
    Optional<PasswordReset> findByResetToken(String resetToken);
    List<PasswordReset> findAllByUsername(String username);
}