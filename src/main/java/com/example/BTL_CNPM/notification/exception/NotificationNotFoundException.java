package com.example.BTL_CNPM.notification.exception;

public class NotificationNotFoundException extends NotificationException {
    public NotificationNotFoundException(String message) {
        super(message, "NOTIFICATION_NOT_FOUND");
    }
}