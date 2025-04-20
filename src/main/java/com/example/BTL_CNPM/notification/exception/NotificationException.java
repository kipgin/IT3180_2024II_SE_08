package com.example.BTL_CNPM.notification.exception;

public abstract class NotificationException extends Exception {
    private final String errorCode;

    public NotificationException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return errorCode;
    }
}