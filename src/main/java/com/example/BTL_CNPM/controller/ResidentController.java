package com.example.BTL_CNPM.controller;

import com.example.BTL_CNPM.model.ApiResponse;
import com.example.BTL_CNPM.model.Household;
import com.example.BTL_CNPM.model.Resident;
import com.example.BTL_CNPM.repository.HouseholdRepository;
import com.example.BTL_CNPM.repository.ResidentRepository;
import com.example.BTL_CNPM.service.HouseholdService;
import com.example.BTL_CNPM.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/residents")
public class ResidentController {
    @Autowired
    private ResidentService residentService;

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private ResidentRepository residentRepository;

    @GetMapping("/get-all")
    public List<Resident> getResidents() {
        return residentService.getAllResidents();
    }

    @PostMapping("/register")
    public boolean addResident(@RequestBody Resident resident) {
        return residentService.addResident(resident);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteResident(@PathVariable Integer id) {
        return residentService.deleteResident(id);
    }

    @GetMapping("/household/{householdId}")
    public List<Resident> getResidentsByHousehold(@PathVariable Integer householdId) {
        return residentService.getResidentsByHouseholdId(householdId);
    }

    @DeleteMapping("/deleteByHousehold/{householdId}")
    public boolean deleteResidentsByHousehold(@PathVariable Integer householdId) {
        System.out.println(householdId + " 111111111111111111111");
        return residentService.deleteAllResidentByHouseholdId(householdId);
    }
}