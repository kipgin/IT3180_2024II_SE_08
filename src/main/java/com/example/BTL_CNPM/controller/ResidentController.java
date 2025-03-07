package com.example.BTL_CNPM.controller;

import com.example.BTL_CNPM.model.Resident;
import com.example.BTL_CNPM.service.ResidentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/residents")
public class ResidentController {
    @Autowired
    private ResidentService residentService;

    @GetMapping
    public List<Resident> getResidents() {
        return residentService.getAllResidents();
    }

    @PostMapping
    public ResponseEntity<Resident> addResident(@RequestBody Resident resident) {
        return ResponseEntity.ok(residentService.addResident(resident));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Long id) {
        residentService.deleteResident(id);
        return ResponseEntity.ok().build();
    }
}