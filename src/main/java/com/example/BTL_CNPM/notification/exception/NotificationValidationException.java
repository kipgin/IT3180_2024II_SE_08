package com.example.BTL_CNPM.notification.exception;

public class NotificationValidationException extends NotificationException {
    private final String fieldName;

    public NotificationValidationException(String message, String errorCode) {
        super(message, errorCode);
        this.fieldName = null;
    }

    public NotificationValidationException(String fieldName, String message, String errorCode) {
        super(message, errorCode);
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return fieldName;
    }
}