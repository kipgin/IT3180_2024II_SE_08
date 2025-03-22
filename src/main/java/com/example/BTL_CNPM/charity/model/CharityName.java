package com.example.BTL_CNPM.charity.model;

import jakarta.persistence.*;

@Entity
@Table(name="charityname")
public class CharityName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String name;

    public CharityName(){

    }

    public CharityName(Integer id, String name){
        this.id=id;
        this.name=name;
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
}
