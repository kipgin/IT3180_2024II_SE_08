package com.example.BTL_CNPM.notification.exception;

public class NotificationAccessException extends NotificationException {
    public NotificationAccessException(String message) {
        super(message, "ACCESS_DENIED");
    }
}