package com.example.BTL_CNPM.notification.exception;

public class NotificationProcessingException extends NotificationException {
    public NotificationProcessingException(String message, Throwable cause) {
        super(message, "PROCESSING_ERROR");
    }
}