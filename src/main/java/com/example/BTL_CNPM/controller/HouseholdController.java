package com.example.BTL_CNPM.controller;

import com.example.BTL_CNPM.model.Household;
import com.example.BTL_CNPM.service.HouseholdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/households")
public class HouseholdController {

    @Autowired
    private HouseholdService householdService;

    @PostMapping("/register/{username}")
    public boolean createHousehold(@PathVariable String username) {
        return householdService.createHousehold(new Household(username));
    }

//    @PutMapping("/update")
//    public boolean updateHousehold(@RequestBody Household household) {
//        return householdService.updateHousehold(household);
//    }

    @DeleteMapping("/delete/{username}")
    public boolean deleteHouseholdByUsername(@PathVariable String username) {
        return householdService.deleteHouseholdByUsername(username);
    }

    @GetMapping("/get-all")
    public List<Household> getAllHouseholds() {
        return householdService.getAllHouseholds();
    }

    @GetMapping("/{username}")
    public Household getHouseholdByUsername(@PathVariable String username) {
        return householdService.getHouseholdByUsername(username);
    }
}
