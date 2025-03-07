package com.example.BTL_CNPM.model;

import jakarta.persistence.*;

@Entity
@Table(name = "residents")
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int birthYear;

    @Enumerated(EnumType.STRING)
    private AccomStatus accomStatus;

    @ManyToOne
    @JoinColumn(name = "household_id")
    private Household household;

    public Resident(Long id, String name, Gender gender, int birthYear, AccomStatus accomStatus, Household household) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthYear = birthYear;
        this.accomStatus = accomStatus;
        this.household = household;
    }

    public Resident() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public AccomStatus getAccomStatus() {
        return accomStatus;
    }

    public void setAccomStatus(AccomStatus accomStatus) {
        this.accomStatus = accomStatus;
    }

    public Household getHousehold() {
        return household;
    }

    public void setHousehold(Household household) {
        this.household = household;
    }
}
