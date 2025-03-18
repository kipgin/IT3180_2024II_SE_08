package com.example.BTL_CNPM.notification.repository;

import com.example.BTL_CNPM.notification.model.NotificationRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Integer> {
    List<NotificationRecipient> findByRecipientUsername(String username);
}
