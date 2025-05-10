package com.example.BTL_CNPM.feemanage.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name="feeName")
public class FeeName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private  String name;

    @Column(nullable = false)
    private Double moneyPerBlock;

    @Column(nullable = false)
    private String block;

    public FeeName(String name, String detail, Double moneyPerBlock) {
        this.name = name;
        this.block = detail;
        this.moneyPerBlock = moneyPerBlock;
    }

    public FeeName(String name, Double moneyPerBlock) {
        this.name = name;
        this.moneyPerBlock = moneyPerBlock;
    }

    public FeeName(){

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

    public String getBlock() {
        return block;
    }

    public void setBlock(String block) {
        this.block = block;
    }

    public Double getMoneyPerBlock() {
        return moneyPerBlock;
    }

    public void setMoneyPerBlock(Double moneyPerBlock) {
        this.moneyPerBlock = moneyPerBlock;
    }
}
