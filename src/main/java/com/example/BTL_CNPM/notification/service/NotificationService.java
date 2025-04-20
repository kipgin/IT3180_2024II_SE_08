package com.example.BTL_CNPM.notification.service;

import com.example.BTL_CNPM.notification.exception.*;
import com.example.BTL_CNPM.notification.model.Notification;
import com.example.BTL_CNPM.notification.model.NotificationType;
import com.example.BTL_CNPM.notification.repository.NotificationRepository;
import com.example.BTL_CNPM.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    // Lấy tất cả thông báo của user
    public List<Notification> getUserNotifications(String userId) throws NotificationValidationException {
        if (userId == null || userId.trim().isEmpty()) {
            throw new NotificationValidationException("User ID không hợp lệ", "INVALID_USER_ID");
        }
        return notificationRepository.findByReceiverId(userId);
    }

    // Lấy thông báo chưa đọc
    public List<Notification> getUnreadNotifications(String userId) throws NotificationValidationException {
        if (userId == null || userId.trim().isEmpty()) {
            throw new NotificationValidationException("User ID không hợp lệ", "INVALID_USER_ID");
        }
        return notificationRepository.findByReceiverIdAndIsReadFalse(userId);
    }

    // Lấy tất cả thông báo chung
    public List<Notification> getGeneralNotifications() {
        return notificationRepository.findByType(NotificationType.GENERAL);
    }

    // Đánh dấu đã đọc
    @Transactional
    public void markAsRead(Long notificationId, String userId)
            throws NotificationNotFoundException, NotificationAccessException {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Không tìm thấy thông báo"));

        if (!notification.getReceiverId().equals(userId)) {
            throw new NotificationAccessException("Không có quyền đánh dấu đã đọc");
        }

        notification.setIsRead(true);
        notificationRepository.save(notification);
    }

    // Tạo thông báo mới
    @Transactional
    public Notification createNotification(Notification notification) throws NotificationValidationException {
        if (!userRepository.existsByUsername(notification.getSenderId())) {
            throw new NotificationValidationException("SenderID không hợp lệ", "SENDER_ID");
        }
        if (notification.getType() == NotificationType.SPECIFIC) {
            if (notification.getType() == NotificationType.SPECIFIC &&
                    (notification.getReceiverId() == null || notification.getReceiverId().trim().isEmpty())) {
                throw new NotificationValidationException("Receiver ID bắt buộc với thông báo SPECIFIC", "MISSING_RECEIVER");
            }
            if (!userRepository.existsByUsername(notification.getReceiverId())) {
                throw new NotificationValidationException("ReceiverID không hợp lệ", "RECEIVER_ID");
            }
        }
        if (notification.getTitle() == null || notification.getTitle().trim().isEmpty()) {
            throw new NotificationValidationException("Tiêu đề không được trống", "EMPTY_TITLE");
        }


        if (notification.getType() == NotificationType.GENERAL &&
                (notification.getReceiverId() != null)) {
            throw new NotificationValidationException("Receiver ID phải null cho thông báo GENERAL", "EXIST_RECEIVER");
        }

        return notificationRepository.save(notification);
    }

    // Xóa thông báo
    @Transactional
    public void deleteNotification(Long notificationId, String requesterId)
            throws NotificationNotFoundException, NotificationAccessException {

        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Không tìm thấy thông báo"));

        if (!notification.getSenderId().equals(requesterId)) {
            throw new NotificationAccessException("Chỉ người gửi mới được xóa thông báo");
        }

        notificationRepository.delete(notification);
    }

    @Transactional
    public void deleteAllBySender(String senderId) {
        notificationRepository.deleteAllBySenderId(senderId);
    }

    @Transactional
    public void deleteAllByReceiver(String receiverId) {
        notificationRepository.deleteAllByReceiverId(receiverId);
    }

    @Transactional
    public void deleteAllNotifications() {
        notificationRepository.deleteAll();
    }
}