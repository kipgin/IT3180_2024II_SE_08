package com.example.BTL_CNPM.auth.controller;

import com.example.BTL_CNPM.auth.model.otp.OTPWordForm;
import com.example.BTL_CNPM.auth.model.users.UsersGmail;
import com.example.BTL_CNPM.auth.service.UsersGmailService;
import com.example.BTL_CNPM.gmail.EmailSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users-gmail")
public class UsersGmailController {
    @Autowired
    private UsersGmailService usersGmailService;

    // Thêm cấu hình EmailSender
    private final EmailSender emailSender;

    public UsersGmailController() {
        // Khởi tạo EmailSender với thông tin email và mật khẩu ứng dụng
        // Nên lấy từ cấu hình application.properties
        this.emailSender = new EmailSender("caohuythinh@gmail.com", "plop alwz udsz opmu");
    }

    @PostMapping("/add")
    public ResponseEntity<UsersGmail> addUser(@RequestBody UsersGmail user) {
        return ResponseEntity.ok(usersGmailService.saveUser(user));
    }

    @GetMapping("/{username}")
    public ResponseEntity<UsersGmail> getUserByUsername(@PathVariable String username) {
        return usersGmailService.getUserByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UsersGmail> getUserByEmail(@PathVariable String email) {
        return usersGmailService.getUserByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        usersGmailService.deleteUser(username);
        return ResponseEntity.ok("User deleted successfully");
    }

    @GetMapping("/all")
    public ResponseEntity<List<UsersGmail>> getAllUsers() {
        return ResponseEntity.ok(usersGmailService.getAllUsers());
    }

    @PostMapping("/{username}/send-second-password")
    public ResponseEntity<String> sendSecondPassword(@PathVariable String username) {
        Optional<UsersGmail> userOptional = usersGmailService.findByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username không tồn tại!");
        }

        UsersGmail user = userOptional.get();
        OTPWordForm otpWordForm = new OTPWordForm(username);
        String output = otpWordForm.generateForm("src/main/resources/form_word/input/otp_template.docx");

        try {
            // Sử dụng EmailSender để gửi email
            emailSender.sendEmailWithAttachment(
                    Arrays.asList(user.getEmail()), // Gửi đến email của user
                    "Mật khẩu cấp 2 - OTP",
                    "Xin chào " + username + ",\n\nĐây là mật khẩu cấp 2 của bạn. Vui lòng không chia sẻ với bất kỳ ai.\n\nTrân trọng!",
                    Arrays.asList(output) // Đính kèm file
            );
            return ResponseEntity.ok("Mật khẩu cấp 2 đã được gửi qua email!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Đã xảy ra lỗi khi gửi email: " + e.getMessage());
        }
    }


}