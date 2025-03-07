package com.example.BTL_CNPM.model;

public class ReportDTO {
    private long totalResidents;
    private long maleResidents;
    private long femaleResidents;

    public ReportDTO(long total, long male, long female) {
        this.totalResidents = total;
        this.maleResidents = male;
        this.femaleResidents = female;
    }
}