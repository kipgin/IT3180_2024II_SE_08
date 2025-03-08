package com.example.BTL_CNPM.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "households")
public class Household {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String ownerUsername;

    private int numOfMembers;

    public Household() {
    }

    public Household(Integer id, String ownerUsername, int numOfMembers) {
        this.id = id;
        this.ownerUsername = ownerUsername;
        this.numOfMembers = numOfMembers;
    }

    public Household(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwnerUsername() {
        return ownerUsername;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUsername = ownerUsername;
    }

    public int getNumOfMembers() {
        return numOfMembers;
    }

    public void setNumOfMembers(int numOfMembers) {
        this.numOfMembers = numOfMembers;
    }
}