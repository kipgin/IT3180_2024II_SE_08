package com.example.BTL_CNPM.notification.repository;

import com.example.BTL_CNPM.notification.model.Notification;
import com.example.BTL_CNPM.notification.model.NotificationType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByReceiverId(String receiverId);
    List<Notification> findByReceiverIdAndIsReadFalse(String receiverId);
    List<Notification> findByType(NotificationType type);

    @Transactional
    @Modifying
    void deleteAllBySenderId(String senderId);

    @Transactional
    @Modifying
    void deleteAllByReceiverId(String receiverId);

    @Transactional
    @Modifying
    void deleteAll();


}