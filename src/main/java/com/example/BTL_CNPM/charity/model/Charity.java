package com.example.BTL_CNPM.charity.model;

import com.example.BTL_CNPM.resident.model.AccomStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
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

    @OneToMany(mappedBy ="charity",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference
    private List<CharitySection> charitySections = new ArrayList<>();

    public Charity(){

    }

    public Charity(Integer id, String ownerUserName, AccomStatus accomStatus){
        this.id=id;
        this.ownerUserName=ownerUserName;
        this.accomStatus=accomStatus;
    }

    //cap nhat cho CharitySection
    public void add(CharitySection section){
        section.setCharity(this);
        this.charitySections.add(section);
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

    public AccomStatus getAccomStatus() {
        return accomStatus;
    }

    public void setAccomStatus(AccomStatus accomStatus) {
        this.accomStatus = accomStatus;
    }

    public List<CharitySection> getCharitySections() {
        return charitySections;
    }

    public void setCharitySections(List<CharitySection> charitySections) {
        this.charitySections = charitySections;
    }
}
