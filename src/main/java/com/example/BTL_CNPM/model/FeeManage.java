package com.example.BTL_CNPM.model;
import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name="feemanage")

public class FeeManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int area;

    @Column(nullable = false)
    private long serviceFeePerSquare;

    @Column(nullable = false)
    private long totalFee;

    @Column(nullable = false,unique = true)
    private String ownerUsername;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccomStatus accom_status;

    public final int num_max=20;

    public final int length_max =30;

    public int num_of_charity;

    final private long managingFeePerSquare =7000;

    public FeeManage(Integer id, int area, long serviceFeePerSquare, String ownerUsername, AccomStatus accom_status, int num_of_charity) {
        this.id = id;
        this.area = area;
        this.serviceFeePerSquare = serviceFeePerSquare;
        this.ownerUsername = ownerUsername;
        this.accom_status = accom_status;
        this.totalFee=area*serviceFeePerSquare+area*managingFeePerSquare;
        this.num_of_charity=num_of_charity;
    }

    public FeeManage(){

    }

    public FeeManage(String ownerUsername){
        this.ownerUsername=ownerUsername;
    }
    public int getArea() {
        return area;
    }

    public void setArea(int area) {
        this.area = area;
    }

    public AccomStatus getAccom_status() {
        return accom_status;
    }

    public void setAccom_status(AccomStatus accom_status) {
        this.accom_status = accom_status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(long totalFee) {
        this.totalFee = totalFee;
    }
}
