package com.example.BTL_CNPM.feepaid.model;
import com.example.BTL_CNPM.resident.model.AccomStatus;
import jakarta.persistence.*;
import jakarta.persistence.criteria.CriteriaBuilder;

@Entity
@Table(name="feepaid")

public class FeePaid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false,unique = true)
    private String ownerUserName;

    @Column(nullable=false)
    private Integer totalFee;


    @Column(nullable = false)
    private String dueTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccomStatus accom_status;

    public FeePaid(int id, String ownerUsername,Integer totalFee, String dueTime, AccomStatus accom_status) {
        this.id = id;
        this.ownerUserName = ownerUsername;
        this.totalFee=totalFee;
        this.dueTime = dueTime;
        this.accom_status = accom_status;
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

    public Integer getTotalFee() {
        return totalFee;
    }

    public void setTotalFee(Integer totalFee) {
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

    public String getOwnerUsername() {
        return ownerUserName;
    }

    public void setOwnerUsername(String ownerUsername) {
        this.ownerUserName = ownerUsername;
    }
}
