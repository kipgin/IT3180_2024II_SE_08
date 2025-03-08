package com.example.BTL_CNPM.repository;

import com.example.BTL_CNPM.model.NotificationRecipient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationRecipientRepository extends JpaRepository<NotificationRecipient, Integer> {
    List<NotificationRecipient> findByRecipientUsername(String username);
}
