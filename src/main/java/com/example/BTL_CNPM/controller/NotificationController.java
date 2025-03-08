package com.example.BTL_CNPM.controller;

import com.example.BTL_CNPM.model.Notification;
import com.example.BTL_CNPM.service.NotificationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notifications")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping("/create")
    public boolean createNotification(@RequestBody Notification notification) {
        return notificationService.createNotification(notification);
    }

    @GetMapping("/all")
    public List<Notification> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @GetMapping("/{id}")
    public Optional<Notification> getNotificationById(@PathVariable Integer id) {
        return notificationService.getNotificationById(id);
    }

    @GetMapping("/user/{username}")
    public List<Notification> getNotificationsBySender(@PathVariable String username) {
        return notificationService.getNotificationsBySender(username);
    }

    @PutMapping("/update/")
    public boolean updateNotification(@RequestBody Notification updatedNotification) {
        return notificationService.updateNotification(updatedNotification);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteNotification(@PathVariable Integer id) {
        return notificationService.deleteNotification(id);
    }

    @PostMapping("/send/{id}")
    public boolean sendNotification(@PathVariable Integer id) {
        return notificationService.sendNotification(id);
    }
}