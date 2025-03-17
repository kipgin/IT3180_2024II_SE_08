//package com.example.BTL_CNPM.model.otp;
//
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "users_gmail")
//public class UsersGmail {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID tự tăng
//    private Integer id;
//
//    @Column(nullable = false)
//    private String username;
//
//    @Column(nullable = false)
//    private String email;
//
//    private String otp; // Mã OTP
//
//    private LocalDateTime otpExpiry; // Thời gian hết hạn OTP
//
//    public UsersGmail() {}
//
//    public UsersGmail(String username, String email) {
//        this.username = username;
//        this.email = email;
//    }
//
//    public UsersGmail(String username, String email, String otp, LocalDateTime otpExpiry) {
//        this.username = username;
//        this.email = email;
//        this.otp = otp;
//        this.otpExpiry = otpExpiry;
//    }
//
//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }
//
//    public String getUsername() {
//        return username;
//    }
//
//    public void setUsername(String username) {
//        this.username = username;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getOtp() {
//        return otp;
//    }
//
//    public void setOtp(String otp) {
//        this.otp = otp;
//    }
//
//    public LocalDateTime getOtpExpiry() {
//        return otpExpiry;
//    }
//
//    public void setOtpExpiry(LocalDateTime otpExpiry) {
//        this.otpExpiry = otpExpiry;
//    }
//
//    public boolean isOtpValid() {
//        return otp != null && otpExpiry != null && LocalDateTime.now().isBefore(otpExpiry);
//    }
//}