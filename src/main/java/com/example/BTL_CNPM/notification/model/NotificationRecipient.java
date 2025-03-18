package com.example.BTL_CNPM.notification.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "notification_recipients")
public class NotificationRecipient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer notificationId;
    private String recipientUsername;

    @Enumerated(EnumType.STRING)
    private NotificationChannel channel;

    private LocalDateTime viewedAt;
    private String response;

    @Enumerated(EnumType.STRING)
    private NotificationStatusRecipient status;

    public NotificationRecipient() {
    }

    public NotificationRecipient(Integer notificationId, String recipientUsername, NotificationChannel channel, NotificationStatusRecipient status) {
        this.notificationId = notificationId;
        this.recipientUsername = recipientUsername;
        this.channel = channel;
        this.status = status;
        this.viewedAt = null;
        this.response = null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public String getRecipientUsername() {
        return recipientUsername;
    }

    public void setRecipientUsername(String recipientUsername) {
        this.recipientUsername = recipientUsername;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public void setChannel(NotificationChannel channel) {
        this.channel = channel;
    }

    public LocalDateTime getViewedAt() {
        return viewedAt;
    }

    public void setViewedAt(LocalDateTime viewedAt) {
        this.viewedAt = viewedAt;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public NotificationStatusRecipient getStatus() {
        return status;
    }

    public void setStatus(NotificationStatusRecipient status) {
        this.status = status;
    }
}