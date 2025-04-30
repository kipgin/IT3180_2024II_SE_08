package com.example.BTL_CNPM.notification.controller;

import com.example.BTL_CNPM.notification.exception.*;
import com.example.BTL_CNPM.notification.model.Notification;
import com.example.BTL_CNPM.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getUserNotifications(@PathVariable String userId) {
        try {
            List<Notification> notifications = notificationService.getUserNotifications(userId);
            return ResponseEntity.ok(notifications);
        } catch (NotificationValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/user/{userId}/unread")
    public ResponseEntity<?> getUnreadNotifications(@PathVariable String userId) {
        try {
            List<Notification> notifications = notificationService.getUnreadNotifications(userId);
            return ResponseEntity.ok(notifications);
        } catch (NotificationValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/general")
    public ResponseEntity<List<Notification>> getGeneralNotifications() {
        return ResponseEntity.ok(notificationService.getGeneralNotifications());
    }

    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        return ResponseEntity.ok(notificationService.getAllNotifications());
    }

    @PostMapping
    public ResponseEntity<?> createNotification(@RequestBody Notification notification) {
        try {
            Notification created = notificationService.createNotification(notification);
            return ResponseEntity.ok(created);
        } catch (NotificationValidationException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PatchMapping("/{notificationId}/read/{userId}")
    public ResponseEntity<?> markAsRead(
            @PathVariable Long notificationId,
            @PathVariable String userId) {
        try {
            notificationService.markAsRead(notificationId, userId);
            return ResponseEntity.ok().build();
        } catch (NotificationNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotificationAccessException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @DeleteMapping("/{notificationId}/{requesterId}")
    public ResponseEntity<?> deleteNotification(
            @PathVariable Long notificationId,
            @PathVariable String requesterId) {
        try {
            notificationService.deleteNotification(notificationId, requesterId);
            return ResponseEntity.noContent().build();
        } catch (NotificationNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (NotificationAccessException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }

    @DeleteMapping("/sender/{senderId}")
    public ResponseEntity<Void> deleteAllBySender(@PathVariable String senderId) {
        notificationService.deleteAllBySender(senderId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/receiver/{receiverId}")
    public ResponseEntity<Void> deleteAllByReceiver(@PathVariable String receiverId) {
        notificationService.deleteAllByReceiver(receiverId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll() {
        notificationService.deleteAllNotifications();
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/send/{notificationId}")
    public void sendNotificationByGmail(@PathVariable Long notificationId) throws NotificationAccessException {
        notificationService.sendNotificationByGmail(notificationId);
    }
}