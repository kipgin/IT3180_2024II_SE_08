package com.example.BTL_CNPM.notification.DTO;

import com.example.BTL_CNPM.notification.model.Notification;
import com.example.BTL_CNPM.notification.model.NotificationType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDTO {
    private Long id;
    private String senderId;
    private String receiverId;
    private String title;
    private String content;
    private LocalDateTime createdAt;
    private Boolean isRead;
    private NotificationType type;
}