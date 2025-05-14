package com.example.BTL_CNPM.logcharitytable;


import com.example.BTL_CNPM.logcharitysection.LogCharitySection;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name="logcharitytable")
public class LogCharityTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @OrderBy("id ASC")
    private Integer id;

    @Column(nullable = false,unique = true)
    private String ownerUserName;

    @OneToMany(mappedBy = "logCharityTable",cascade = CascadeType.ALL,orphanRemoval = true)
    @OrderBy("id ASC")
    @JsonManagedReference
    private List<LogCharitySection> logCharitySections = new ArrayList<>();

    public LogCharityTable(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public LogCharityTable (){

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public List<LogCharitySection> getLogCharitySections() {
        return logCharitySections;
    }

    public void setLogCharitySections(List<LogCharitySection> logCharitySections) {
        this.logCharitySections = logCharitySections;
    }

    public void add(LogCharitySection logCharitySection){
        this.logCharitySections.add(logCharitySection);
        logCharitySection.setLogCharityTable(this);

    }
}
