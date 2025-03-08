package com.example.BTL_CNPM.repository;

import com.example.BTL_CNPM.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
    List<Notification> findAllBySenderId(String senderId);
}
