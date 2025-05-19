package com.example.BTL_CNPM.logcharitysection;


import com.example.BTL_CNPM.logcharitytable.LogCharityTable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name="logcharitysection")
public class LogCharitySection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @CreationTimestamp
    private LocalDateTime timeCreate;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer donateMoney;

    @ManyToOne
    @JoinColumn( name="logcharitytable_id",nullable = true)
    @JsonBackReference
    private LogCharityTable logCharityTable;

    public LogCharitySection(String name) {
        this.name = name;
    }

    public LogCharitySection(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LogCharityTable getLogCharityTable() {
        return logCharityTable;
    }

    public void setLogCharityTable(LogCharityTable logCharityTable) {
        this.logCharityTable = logCharityTable;
    }

    public LocalDateTime getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(LocalDateTime timeCreate) {
        this.timeCreate = timeCreate;
    }

    public Integer getDonateMoney() {
        return donateMoney;
    }

    public void setDonateMoney(Integer donateMoney) {
        this.donateMoney = donateMoney;
    }
}
