package com.example.BTL_CNPM.charity.model;

import com.example.BTL_CNPM.resident.model.AccomStatus;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="charity")

public class Charity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String ownerUserName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccomStatus accomStatus;

    @ElementCollection
    private List<String> listOfCharity;

    @ElementCollection
    private List<Integer> donateMoney;

    public Charity(){

    }

    public Charity(Integer id, String ownerUserName, AccomStatus accomStatus, List<String> listOfCharity, List<Integer> donateMoney) {
        this.id = id;
        this.ownerUserName = ownerUserName;
        this.accomStatus = accomStatus;
        this.listOfCharity = listOfCharity;
        this.donateMoney = donateMoney;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AccomStatus getAccomStatus() {
        return accomStatus;
    }

    public void setAccomStatus(AccomStatus accomStatus) {
        this.accomStatus = accomStatus;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }

    public List<String> getListOfCharity() {
        return listOfCharity;
    }

    public void setListOfCharity(List<String> listOfCharity) {
        this.listOfCharity = listOfCharity;
    }

    public List<Integer> getDonateMoney() {
        return donateMoney;
    }

    public void setDonateMoney(List<Integer> donateMoney) {
        this.donateMoney = donateMoney;
    }
}
