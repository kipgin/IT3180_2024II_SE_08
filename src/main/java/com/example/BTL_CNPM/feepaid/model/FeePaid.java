package com.example.BTL_CNPM.feepaid.model;
import com.example.BTL_CNPM.resident.model.AccomStatus;
import jakarta.persistence.*;

@Entity
@Table(name="feepaid")

public class FeePaid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String ownerUserName;

    @Column(nullable=false)
    private Long totalFee;


    @Column(nullable = false)
    private String dueTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccomStatus accom_status;

    public final int num_max=20;

    public final int length_max =30;

    public int num_of_charity;


    public FeePaid(int id, String ownerUsername,Long totalFee, String dueTime, AccomStatus accom_status, int num_of_charity) {
        this.id = id;
        this.ownerUserName = ownerUsername;
        this.totalFee=totalFee;
        this.dueTime = dueTime;
        this.accom_status = accom_status;
        this.num_of_charity = num_of_charity;
    }

    public FeePaid(){

    }

    public FeePaid(String ownerUsername){
        this.ownerUserName=ownerUsername;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Long totalFee) {
        this.totalFee = totalFee;
    }

    public String getDueTime() {
        return dueTime;
    }

    public void setDueTime(String dueTime) {
        this.dueTime = dueTime;
    }

    public AccomStatus getAccom_status() {
        return accom_status;
    }

    public void setAccom_status(AccomStatus accom_status) {
        this.accom_status = accom_status;
    }

    public int getNum_of_charity() {
        return num_of_charity;
    }

    public void setNum_of_charity(int num_of_charity) {
        this.num_of_charity = num_of_charity;
    }

    public String getOwnerUsername() {
        return ownerUserName;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUserName = ownerUsername;
    }
}
