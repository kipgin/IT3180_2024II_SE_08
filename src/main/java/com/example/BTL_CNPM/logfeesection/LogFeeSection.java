package com.example.BTL_CNPM.logfeesection;


import com.example.BTL_CNPM.logfeetable.model.LogFeeTable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jdk.jfr.Timestamp;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name="logfeesection")
public class LogFeeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @CreationTimestamp
    private LocalDateTime timeCreate;

    @Column(nullable = false)
    private Boolean paid;

    @Column(nullable = false)
    private Double FeePaid;

    @ManyToOne
    @JoinColumn(name="logfeetable_id",nullable = true)
    @JsonBackReference
    public LogFeeTable logFeeTable;


    public LogFeeSection(LogFeeTable logFeeTable, LocalDateTime timeCreate) {
        this.logFeeTable = logFeeTable;
        this.timeCreate= timeCreate;
    }

    public LogFeeSection(LocalDateTime timeCreate) {
        this.timeCreate=timeCreate;
    }

    public LogFeeSection(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDateTime getTimeCreate() {
        return timeCreate;
    }

    public void setTimeCreate(LocalDateTime timeCreate) {
        this.timeCreate = timeCreate;
    }

    public LogFeeTable getLogFeeTable() {
        return logFeeTable;
    }

    public void setLogFeeTable(LogFeeTable logFeeTable) {
        this.logFeeTable = logFeeTable;
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Double getFeePaid() {
        return FeePaid;
    }

    public void setFeePaid(Double feePaid) {
        FeePaid = feePaid;
    }
}
