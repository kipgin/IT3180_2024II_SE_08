package com.example.BTL_CNPM.service;

import com.example.BTL_CNPM.model.Resident;
import com.example.BTL_CNPM.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResidentService {
    @Autowired
    private ResidentRepository residentRepository;

    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    public Resident addResident(Resident resident) {
        return residentRepository.save(resident);
    }

    public void deleteResident(Long id) {
        residentRepository.deleteById(id);
    }
}

