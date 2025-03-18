package com.example.BTL_CNPM.resident.model;

import jakarta.persistence.*;


@Entity
@Table(name = "residents")
public class Resident {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private int birthYear;

    @Enumerated(EnumType.STRING)
    private AccomStatus accomStatus;

    @Column(name = "household_id", nullable = true)
    private Integer householdId; // Chỉ lưu ID thay vì đối tượng Household

    public Resident(Integer id, String name, Gender gender, int birthYear, AccomStatus accomStatus, Integer householdId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthYear = birthYear;
        this.accomStatus = accomStatus;
        this.householdId = householdId;
    }

    public Resident() {
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

    public Integer getHouseholdId() {
        return householdId;
    }

    public void setHouseholdId(Integer householdId) {
        this.householdId = householdId;
    }
}
