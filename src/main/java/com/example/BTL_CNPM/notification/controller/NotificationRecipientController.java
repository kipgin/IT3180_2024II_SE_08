package com.example.BTL_CNPM.notification.controller;


import com.example.BTL_CNPM.notification.model.NotificationRecipient;
import com.example.BTL_CNPM.notification.service.NotificationRecipientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification-recipients")
public class NotificationRecipientController {

    @Autowired
    private NotificationRecipientService notificationRecipientService;

    @PostMapping("/send")
    public boolean sendNotification(@RequestBody NotificationRecipient notificationRecipient) {
        return notificationRecipientService.sendNotification(notificationRecipient);
    }

    @GetMapping("/user/{username}")
    public List<NotificationRecipient> getUserNotifications(@PathVariable String username) {
        return notificationRecipientService.getNotificationsByUser(username);
    }

    @PutMapping("/view/{id}")
    public boolean markAsViewed(@PathVariable Integer id) {
        return notificationRecipientService.markAsViewed(id);
    }

    @PutMapping("/respond/{id}")
    public boolean respondToNotification(@PathVariable Integer id, @RequestParam String response) {
        return notificationRecipientService.respondToNotification(id, response);
    }

    @GetMapping("/all")
    public List<NotificationRecipient> getAllNotifications() {
        return notificationRecipientService.getAllNotifications();
    }
}