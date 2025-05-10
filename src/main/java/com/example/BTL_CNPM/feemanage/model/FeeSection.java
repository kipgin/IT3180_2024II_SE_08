package com.example.BTL_CNPM.feemanage.model;

import com.example.BTL_CNPM.feemanage.model.FeeManage;
import com.example.BTL_CNPM.charity.model.Charity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name="feeSection")
public class FeeSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = false)
    private String name;

    @Column(nullable = false)
    private Double blockUsed;

    @ManyToOne
    @JoinColumn(name="feemange_id",nullable = false)
    @JsonBackReference(value = "feeManage-section")
    private FeeManage feeManage;

    @ManyToOne
    @JoinColumn(name = "feename_id")
    @JsonBackReference
    private FeeName feeName;

    public FeeSection(String name, Double blockUsed) {
        this.name = name;
        this.blockUsed = blockUsed;
    }

    public FeeSection(){

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

    public void setName(String name) {
        this.name = name;
    }

    public Double getBlockUsed() {
        return blockUsed;
    }

    public void setBlockUsed(Double blockUsed) {
        this.blockUsed = blockUsed;
    }

    public FeeManage getFeeManage() {
        return feeManage;
    }

    public void setFeeManage(FeeManage feeManage) {
        this.feeManage = feeManage;
    }

    public FeeName getFeeName() {
        return feeName;
    }

    public void setFeeName(FeeName feeName) {
        this.feeName = feeName;
    }
}
