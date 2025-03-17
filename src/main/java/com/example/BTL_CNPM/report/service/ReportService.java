package com.example.BTL_CNPM.report.service;

import com.example.BTL_CNPM.resident.model.Gender;
import com.example.BTL_CNPM.report.model.ReportDTO;
import com.example.BTL_CNPM.resident.model.Resident;
import com.example.BTL_CNPM.resident.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {
    @Autowired
    private ResidentRepository residentRepository;

    public ReportDTO generateReport() {
        List<Resident> residents = residentRepository.findAll();

        long total = residents.size();
        long maleCount = residents.stream().filter(r -> r.getGender() == Gender.MALE).count();
        long femaleCount = total - maleCount;

        return new ReportDTO(total, maleCount, femaleCount);
    }
}
