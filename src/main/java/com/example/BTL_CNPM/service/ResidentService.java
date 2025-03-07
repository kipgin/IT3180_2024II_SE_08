package com.example.BTL_CNPM.service;

import com.example.BTL_CNPM.model.Resident;
import com.example.BTL_CNPM.repository.HouseholdRepository;
import com.example.BTL_CNPM.repository.ResidentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResidentService {
    @Autowired
    private ResidentRepository residentRepository;

    @Autowired
    private HouseholdRepository householdRepository;

    public List<Resident> getAllResidents() {
        return residentRepository.findAll();
    }

    public boolean addResident(Resident resident) {
        boolean householdExists = householdRepository.existsByOwnerUsername(resident.getHousehold().getOwnerUsername());

        if (!householdExists) {
            return false;
        }

        residentRepository.save(resident);
        return true;
    }


    public boolean deleteResident(Integer id) {
        Optional<Resident> resident = residentRepository.findById(id);
        if (resident.isPresent()) {
            residentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}

