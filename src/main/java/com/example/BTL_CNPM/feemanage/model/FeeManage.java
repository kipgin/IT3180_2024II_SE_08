package com.example.BTL_CNPM.feemanage.model;


import com.example.BTL_CNPM.resident.model.AccomStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@Entity
@Table(name="feeManage")
public class FeeManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String ownerUserName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccomStatus accom_status;

//    @Column(nullable = false)
//    private int serviceFeePerSquare;
    @Column(nullable = true)
    private Double totalFee;

    @Column(nullable = true)
    private Boolean paid;

    @OneToMany(mappedBy ="feeManage",cascade = CascadeType.ALL,orphanRemoval = true)
    @JsonManagedReference(value = "feeManage-section")
    private List<FeeSection> feeSections = new ArrayList<>();

    public FeeManage(String ownerUserName, AccomStatus accom_status, Double totalFee) {
        this.ownerUserName = ownerUserName;
        this.accom_status = accom_status;
        //this.area = area;
        this.paid=false;
        this.totalFee = totalFee;
        this.feeSections=new ArrayList<>();
    }

    public FeeManage(String ownerUserName,AccomStatus accom_status){
        this.ownerUserName=ownerUserName;
        this.accom_status=accom_status;
        this.totalFee=0.0;
        this.paid=false;
        this.feeSections=new ArrayList<>();
    }

    public FeeManage(){

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

    public AccomStatus getAccom_status() {
        return accom_status;
    }

    public void setAccom_status(AccomStatus accom_status) {
        this.accom_status = accom_status;
    }

//    public int getArea() {
//        return area;
//    }
//
//    public void setArea(int area) {
//        this.area = area;
//    }

    public List<FeeSection> getFeeSections() {
        return feeSections;
    }

    public void setFeeSections(List<FeeSection> feeSections) {
        this.feeSections = feeSections;
    }

    public Double getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Double totalFee) {
        this.totalFee = totalFee;
    }

    public void addSection(FeeSection feeSection){
        feeSection.setFeeManage(this);
        feeSection.setFeeName(feeSection.getFeeName());
        this.feeSections.add(feeSection);
    }

    public void updateTotalFee(){
        if(this.feeSections.isEmpty()){
            totalFee = 0.0;
            return;
        }
        this.totalFee=feeSections.stream()
                .map(section -> section.getFeeName().getMoneyPerBlock() * section.getBlockUsed())
                .reduce(0.0, Double::sum);
    }

    public Boolean getPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }
}
