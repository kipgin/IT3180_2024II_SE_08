package com.example.BTL_CNPM.notification.repository;

import com.example.BTL_CNPM.notification.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllBySenderId(String senderId);
}
