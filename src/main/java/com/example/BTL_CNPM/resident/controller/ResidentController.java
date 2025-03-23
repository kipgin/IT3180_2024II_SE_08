package com.example.BTL_CNPM.resident.controller;

import com.example.BTL_CNPM.resident.model.Resident;
import com.example.BTL_CNPM.household.repository.HouseholdRepository;
import com.example.BTL_CNPM.resident.repository.ResidentRepository;
import com.example.BTL_CNPM.resident.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/report/number_of_residents")
    public int getNumberOfResidents() {
        return (int) residentRepository.count();
    }
}