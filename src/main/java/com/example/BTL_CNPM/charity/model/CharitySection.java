package com.example.BTL_CNPM.charity.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name="charitysection")
public class CharitySection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int donate;

    @ManyToOne
    @JoinColumn(name="charity_id",nullable = false)
    @JsonBackReference
    private Charity charity;

    public CharitySection(){

    }

    public CharitySection(String name, int donate){
        this.name=name;
        this.donate=donate;
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

    public int getDonate() {
        return donate;
    }

    public void setDonate(int donate) {
        this.donate = donate;
    }

    public Charity getCharity() {
        return charity;
    }

    public void setCharity(Charity charity) {
        this.charity = charity;
    }
}
