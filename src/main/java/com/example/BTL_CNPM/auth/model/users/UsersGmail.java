package com.example.BTL_CNPM.auth.model.users;

import jakarta.persistence.*;

@Entity
@Table(name = "users_gmail")
public class UsersGmail {
    @Id
    private String username;  // PRIMARY KEY

    @Column(nullable = false, unique = true)
    private String email;  // UNIQUE

    public UsersGmail() {}

    public UsersGmail(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
