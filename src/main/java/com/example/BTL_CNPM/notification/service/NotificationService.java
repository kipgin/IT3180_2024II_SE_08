package com.example.BTL_CNPM.notification.service;

import com.example.BTL_CNPM.notification.model.Notification;
import com.example.BTL_CNPM.notification.model.NotificationStatus;
import com.example.BTL_CNPM.notification.repository.NotificationRepository;
import com.example.BTL_CNPM.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public boolean createNotification(Notification notification) {
        // Kiểm tra sender_id có tồn tại trong UserRepository không
        boolean senderExists = userRepository.existsByUsername(notification.getSenderId());
        if (!senderExists) {
            return false; // Nếu không có user, trả về false
        }
        // Nếu user tồn tại, đặt trạng thái là PENDING và lưu vào DB
        notification.setStatus(NotificationStatus.PENDING);
        notificationRepository.save(notification);
        return true;
    }

    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    public Optional<Notification> getNotificationById(Integer id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> getNotificationsBySender(String senderId) {
        return notificationRepository.findAllBySenderId(senderId);
    }

    public boolean updateNotification(Notification updatedNotification) {
        Optional<Notification> existingNotification = notificationRepository.findById(updatedNotification.getNotificationId());
        if (existingNotification.isPresent()) {
            Notification notification = existingNotification.get();
            notification.setTitle(updatedNotification.getTitle());
            notification.setContent(updatedNotification.getContent());
            notification.setType(updatedNotification.getType());
            notificationRepository.save(notification);
            return true;
        }
        return false;
    }

    public boolean deleteNotification(Integer id) {
        if (notificationRepository.existsById(id)) {
            notificationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public boolean sendNotification(Integer id) {
        Optional<Notification> notificationOpt = notificationRepository.findById(id);
        if (notificationOpt.isPresent()) {
            Notification notification = notificationOpt.get();
            notification.setStatus(NotificationStatus.SENT);
            notification.setSentAt(LocalDateTime.now());
            notificationRepository.save(notification);
            return true;
        }
        return false;
    }
}