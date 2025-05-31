package com.example.BTL_CNPM.schedulefee;


import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name="schedulefee")
public class ScheduleFee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer day ;

    @Column(nullable = false,unique = true)
    private String name;

    public ScheduleFee(Integer time, String name) {
        this.day = time;
        this.name = name;
    }

    public ScheduleFee(){

    }

    public ScheduleFee(String name) {
        this.name = name;
    }

    public ScheduleFee(Integer time){
        this.day=time;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
