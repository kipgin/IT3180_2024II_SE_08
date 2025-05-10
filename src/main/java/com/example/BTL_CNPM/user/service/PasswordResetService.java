package com.example.BTL_CNPM.user.service;

import com.example.BTL_CNPM.gmail.EmailSender;
import com.example.BTL_CNPM.gmail.model.users.UsersGmail;
import com.example.BTL_CNPM.gmail.service.UsersGmailService;
import com.example.BTL_CNPM.user.model.PasswordReset;
import com.example.BTL_CNPM.user.repository.PasswordResetRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PasswordResetService {

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    @Autowired
    private UsersGmailService usersGmailService;

    // Tạo token reset mật khẩu
    public boolean createPasswordResetToken(String username) {
        // Tạo token ngẫu nhiên
        String token = UUID.randomUUID().toString();

        // Thời gian hết hạn (5 phút từ bây giờ)
//        LocalDateTime expiryTime = ;

        PasswordReset passwordReset = new PasswordReset(username, token, LocalDateTime.now().plusSeconds(300), LocalDateTime.now());
        deleteOldTokens(username);
        passwordResetRepository.save(passwordReset);
        return sendPasswordResetToken(username, token);
    }

    public boolean verifyToken(String token, String username) {
        // Lấy token mới nhất của người dùng
        Optional<PasswordReset> passwordResetOpt = passwordResetRepository.findByUsername(username)
                .stream()
                .max(Comparator.comparing(PasswordReset::getExpiryTime)); // Lấy token mới nhất (dựa trên thời gian hết hạn)

        // Kiểm tra nếu không tìm thấy token nào
        if (passwordResetOpt.isEmpty()) {
            return false; // Không có token nào
        }

        PasswordReset passwordReset = passwordResetOpt.get();

        // Kiểm tra xem token có khớp với token mới nhất không
        if (!passwordReset.getResetToken().equals(token)) {
            return false; // Không phải token mới nhất
        }

        // Kiểm tra token đã hết hạn chưa (5 phút)
        if (passwordReset.getExpiryTime().isBefore(LocalDateTime.now())) {
            return false; // Token đã hết hạn
        }

        // Token hợp lệ
        return true;
    }



    // Xóa token cũ sau khi tạo token mới
    public void deleteOldTokens(String username) {
        // Tìm tất cả các token của người dùng và xóa các token cũ
        List<PasswordReset> allTokens = passwordResetRepository.findAllByUsername(username);
        for (PasswordReset token : allTokens) {
            passwordResetRepository.delete(token);
        }
    }

    public boolean sendPasswordResetToken(String username, String resetToken) {
        // Tạo đối tượng EmailSender với email và mật khẩu ứng dụng của bạn

        EmailSender sender = new EmailSender("caohuythinh@gmail.com", "plop alwz udsz opmu");

        try {
            // Lấy thông tin người dùng từ cơ sở dữ liệu
            UsersGmail userGmail = usersGmailService.getUserByUsername(username).get();

            // Tạo nội dung email với mã reset mật khẩu
            String emailBody = "Your password reset code is: " + resetToken + ", valid for 5 minutes.";

            // Gửi email
            sender.sendEmail(userGmail.getEmail(), "Password Reset Code", emailBody);

            System.out.println("Password reset code sent successfully!");
            return true;
        } catch (MessagingException e) {
            System.err.println("Failed to send password reset code email: " + e.getMessage());
            return false;
        }
    }
}
