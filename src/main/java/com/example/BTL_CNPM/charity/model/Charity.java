package com.example.BTL_CNPM.charity.model;

import com.example.BTL_CNPM.resident.model.AccomStatus;
import jakarta.persistence.*;

import java.util.ArrayList;

@Entity
@Table(name="charityowner")

public class CharityOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String ownerUserName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccomStatus accomStatus;

    @OneToMany(mappedBy ="charityowner",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<CharitySection> charitySections = new ArrayList<>();
}
