package com.example.BTL_CNPM.duetimefee.model;

import jakarta.persistence.*;
import jdk.jfr.Timestamp;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name="duetimefee")
public class DueTimeFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;

    @Column(nullable =true)
    private LocalDateTime dueTime;

    @Column(nullable = false)
    private String recurringTime;

    @Column(nullable = true)
    private LocalDateTime lastTime;


    public DueTimeFee(String feeName) {
        this.name = feeName;
    }

    public DueTimeFee(Integer id, String feeName) {
        this.id = id;
        this.name = feeName;
    }

    public DueTimeFee(String recurringTime, Integer id, String name) {
        this.recurringTime = recurringTime;
        this.id = id;
        this.name = name;
    }

    public DueTimeFee(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setFeeName(String feeName) {
        this.name = feeName;
    }

    public LocalDateTime getDueTime() {
        return dueTime;
    }

    public void setDueTime(LocalDateTime dueTime) {
        this.dueTime = dueTime;
    }

    public String getRecurringTime() {
        return recurringTime;
    }

    public void setRecurringTime(String recurringTime) {
        this.recurringTime = recurringTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getLastTime() {
        return lastTime;
    }

    public void setLastTime(LocalDateTime lastPaidTime) {
        this.lastTime = lastPaidTime;
    }
}
