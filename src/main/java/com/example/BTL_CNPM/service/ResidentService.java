package com.example.BTL_CNPM.service;

import com.example.BTL_CNPM.model.Household;
import com.example.BTL_CNPM.model.Resident;
import com.example.BTL_CNPM.repository.HouseholdRepository;
import com.example.BTL_CNPM.repository.ResidentRepository;
import jakarta.transaction.Transactional;
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
        if (resident.getHouseholdId() == null) {
            return false;
        }
        Optional<Household> householdOpt = householdRepository.findById(resident.getHouseholdId());
        if (householdOpt.isEmpty()) {
            return false;
        }
        residentRepository.save(resident);
        Household household = householdOpt.get();
        household.setNumOfMembers(household.getNumOfMembers() + 1);
        householdRepository.save(household);
        return true;
    }


    public boolean deleteResident(Integer id) {
        Optional<Resident> residentOpt = residentRepository.findById(id);
        if (residentOpt.isPresent()) {
            residentRepository.deleteById(id);
            Resident resident = residentOpt.get();
            Optional<Household> householdOpt = householdRepository.findById(resident.getHouseholdId());
            Household household = householdOpt.get();
            household.setNumOfMembers(household.getNumOfMembers() - 1);
            householdRepository.save(household);
            return true;
        }
        return false;
    }
    @Transactional
    public boolean deleteAllResidentByHouseholdId(Integer householdId) {
        if (residentRepository.existsByHouseholdId(householdId)) {
            residentRepository.deleteAllByHouseholdId(householdId);
            Optional<Household> householdOpt = householdRepository.findById(householdId);
            Household household = householdOpt.get();
            household.setNumOfMembers(0);
            householdRepository.save(household);
            return true;
        }
        return false;
    }

    public List<Resident> getResidentsByHouseholdId(Integer householdId) {
        return residentRepository.findByHouseholdId(householdId);
    }
}

