package com.example.BTL_CNPM.service;

import com.example.BTL_CNPM.model.Gender;
import com.example.BTL_CNPM.model.ReportDTO;
import com.example.BTL_CNPM.model.Resident;
import com.example.BTL_CNPM.repository.ResidentRepository;
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
