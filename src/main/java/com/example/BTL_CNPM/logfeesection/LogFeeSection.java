package com.example.BTL_CNPM.logfeesection;


import com.example.BTL_CNPM.logfeetable.model.LogFeeTable;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name="logfeesection")
public class LogFeeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false)
    public String logName;


    @ManyToOne
    @JoinColumn(name="logfeetable_id",nullable = true)
    @JsonBackReference
    public LogFeeTable logFeeTable;


    public LogFeeSection(LogFeeTable logFeeTable, String logName) {
        this.logFeeTable = logFeeTable;
        this.logName = logName;
    }

    public LogFeeSection(String logName) {
        this.logName = logName;
    }

    public LogFeeSection(){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public LogFeeTable getLogFeeTable() {
        return logFeeTable;
    }

    public void setLogFeeTable(LogFeeTable logFeeTable) {
        this.logFeeTable = logFeeTable;
    }
}
