package com.example.BTL_CNPM.feemanage.model;
import com.example.BTL_CNPM.resident.model.AccomStatus;
import jakarta.persistence.*;

@Entity
@Table(name="feemanage")
public class FeeManage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private int area;

    @Column(nullable = false)
    private int serviceFeePerSquare;

    @Column(nullable = false)
    private int totalFee;

    @Column(nullable = false,unique = true)
    private String ownerUserName;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccomStatus accom_status;

    //final private long managingFeePerSquare =7000;

    public FeeManage(Integer id, int area, int serviceFeePerSquare, String ownerUserName, AccomStatus accom_status) {
        this.id = id;
        this.area = area;
        this.serviceFeePerSquare = serviceFeePerSquare;
        this.ownerUserName = ownerUserName;
        this.accom_status = accom_status;
        this.totalFee=0;
    }
    public FeeManage(int area, int serviceFeePerSquare, String ownerUserName, AccomStatus accom_status) {
        this.area = area;
        this.serviceFeePerSquare = serviceFeePerSquare;
        this.ownerUserName = ownerUserName;
        this.accom_status = accom_status;
        this.totalFee=0;
    }

    public FeeManage(){

    }

    public FeeManage(String ownerUserName){
        this.ownerUserName=ownerUserName;
    }

    public void updateFee(){
        this.totalFee += this.area * serviceFeePerSquare + this.area * 7000;
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

    public void setTotalFee(int totalFee) {
        this.totalFee = totalFee;
    }

    public long getServiceFeePerSquare() {
        return serviceFeePerSquare;
    }

    public void setServiceFeePerSquare(int serviceFeePerSquare) {
        this.serviceFeePerSquare = serviceFeePerSquare;
    }

    public String getOwnerUserName() {
        return ownerUserName;
    }

    public void setOwnerUserName(String ownerUserName) {
        this.ownerUserName = ownerUserName;
    }
}
