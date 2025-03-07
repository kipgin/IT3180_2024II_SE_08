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

    @GetMapping
    public List<Resident> getResidents() {
        return residentService.getAllResidents();
    }

//    @PostMapping("/register")
//    public ApiResponse addResident(@RequestBody Resident resident) {
//        // Kiểm tra household có tồn tại không
//        Optional<Household> householdOpt = householdRepository.findById(resident.getHousehold().getId());
//        if (householdOpt.isEmpty()) {
//            return new ApiResponse(false, "Household ID không tồn tại");
//        }
//
//        // Kiểm tra xem cư dân đã tồn tại trong household chưa
//        boolean exists = residentRepository.existsByNameAndHouseholdId(resident.getName(), resident.getHouseholdId());
//        if (exists) {
//            return new ApiResponse(false, "Cư dân đã tồn tại trong hộ này");
//        }
//
//        // Lưu vào DB
//        residentRepository.save(resident);
//        return new ApiResponse(true, "Thêm cư dân thành công");
//    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResident(@PathVariable Integer id) {
        residentService.deleteResident(id);
        return ResponseEntity.ok().build();
    }
}