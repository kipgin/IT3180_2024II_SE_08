package com.example.BTL_CNPM.user.model;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset")
public class PasswordReset {

    @Id
    private String username;

    @Column(nullable = false)
    private String resetToken;

    @Column(nullable = false)
    private LocalDateTime expiryTime;

    @CreationTimestamp
    private LocalDateTime createdAt;

    // Getters và Setters
    public PasswordReset() {}

    public PasswordReset(String username, String resetToken, LocalDateTime expiryTime, LocalDateTime createdAt) {
        this.username = username;
        this.resetToken = resetToken;
        this.expiryTime = expiryTime;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}