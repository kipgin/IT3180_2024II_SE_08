package com.example.BTL_CNPM.logfeetable.model;


import com.example.BTL_CNPM.logfeesection.LogFeeSection;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name ="logfeetable")
public class LogFeeTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;

    @Column(nullable = false,unique = true)
    public String ownerUserName;

    @OneToMany(mappedBy = "logFeeTable",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<LogFeeSection> logFeeSectionList =new ArrayList<>();

    public LogFeeTable(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }


    public LogFeeTable() {
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public List<LogFeeSection> getLogFeeSectionList() {
        return logFeeSectionList;
    }

    public void setLogFeeSectionList(List<LogFeeSection> logFeeSectionList) {
        this.logFeeSectionList = logFeeSectionList;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void add(LogFeeSection logFeeSection){
        logFeeSection.setLogFeeTable(this);
        this.getLogFeeSectionList().add(logFeeSection);
    }
}
