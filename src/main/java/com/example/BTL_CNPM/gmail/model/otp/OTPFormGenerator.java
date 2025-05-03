package com.example.BTL_CNPM.gmail.model.otp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public abstract class OTPFormGenerator {
    protected String userName;
    protected String otpCode;
    protected LocalDateTime timestamp;

    // Constructor
    public OTPFormGenerator(String userName) {
        this.userName = userName;
        this.otpCode = generateOTP(6);
        this.timestamp = LocalDateTime.now();
    }

    // Hàm abstract để các lớp con bắt buộc triển khai
    public abstract String generateForm(String filePath);

    // Hàm lấy thời gian format chuẩn
    protected String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return timestamp.format(formatter);
    }

    protected String getFormattedTimeStampForGenerate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss");
        return timestamp.format(formatter);
    }

    // Hàm tạo mã OTP ngẫu nhiên
    public static String generateOTP(int length) {
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < length; i++) {
            otp.append((int) (Math.random() * 10)); // Số từ 0-9
        }
        return otp.toString();
    }
}