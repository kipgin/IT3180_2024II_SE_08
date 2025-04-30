package com.example.BTL_CNPM.gmail.repository;

import com.example.BTL_CNPM.gmail.model.users.UsersGmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersGmailRepository extends JpaRepository<UsersGmail, String> {
    Optional<UsersGmail> findByEmail(String email);
    public Optional<UsersGmail> findByUsername(String username);
}
