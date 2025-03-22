package com.example.BTL_CNPM.auth.controller;

import com.example.BTL_CNPM.auth.model.otp.OTPWordForm;
import com.example.BTL_CNPM.auth.model.users.UsersGmail;
import com.example.BTL_CNPM.auth.service.UsersGmailService;
import com.example.BTL_CNPM.gmail.EzGmail;
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
        OTPWordForm otpWordForm = new OTPWordForm(username);
        String output = otpWordForm.generateForm("src/main/resources/form_word/input/otp_template.docx");
        try {
            List<File> attachments = Arrays.asList(new File(output));
            EzGmail.sendEmailWithAttachment("thinhhuycaotraistorm@gmail.com", "OTP", "Không gửi mã bên dưới cho ai!", attachments);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok("Mật khẩu cấp 2 đã được gửi qua email!");
    }
}
