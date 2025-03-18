package com.example.BTL_CNPM.notification.service;

import com.example.BTL_CNPM.notification.model.NotificationRecipient;
import com.example.BTL_CNPM.notification.model.NotificationStatusRecipient;
import com.example.BTL_CNPM.notification.repository.NotificationRecipientRepository;
import com.example.BTL_CNPM.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationRecipientService {

    @Autowired
    private NotificationRecipientRepository notificationRecipientRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean sendNotification(NotificationRecipient notificationRecipient) {
        // Kiểm tra xem người nhận có tồn tại không
        if (!userRepository.existsByUsername(notificationRecipient.getRecipientUsername())) {
            return false;
        }

        // Lưu thông báo gửi đi
        notificationRecipientRepository.save(notificationRecipient);
        return true;
    }

    public List<NotificationRecipient> getNotificationsByUser(String username) {
        return notificationRecipientRepository.findByRecipientUsername(username);
    }

    public boolean markAsViewed(Integer id) {
        Optional<NotificationRecipient> notificationOpt = notificationRecipientRepository.findById(id);
        if (notificationOpt.isPresent()) {
            NotificationRecipient notification = notificationOpt.get();
            notification.setViewedAt(LocalDateTime.now());
            notification.setStatus(NotificationStatusRecipient.VIEWED);
            notificationRecipientRepository.save(notification);
            return true;
        }
        return false;
    }

    public boolean respondToNotification(Integer id, String response) {
        Optional<NotificationRecipient> notificationOpt = notificationRecipientRepository.findById(id);
        if (notificationOpt.isPresent()) {
            NotificationRecipient notification = notificationOpt.get();
            notification.setResponse(response);
            notificationRecipientRepository.save(notification);
            return true;
        }
        return false;
    }

    public List<NotificationRecipient> getAllNotifications() {
        return notificationRecipientRepository.findAll();
    }
}
