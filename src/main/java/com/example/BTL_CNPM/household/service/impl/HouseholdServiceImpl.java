package com.example.BTL_CNPM.household.service.impl;

import com.example.BTL_CNPM.charity.service.CharityService;
import com.example.BTL_CNPM.feemanage.service.FeeManageService;
import com.example.BTL_CNPM.household.model.Household;
import com.example.BTL_CNPM.household.repository.HouseholdRepository;
import com.example.BTL_CNPM.user.repository.UserRepository;
import com.example.BTL_CNPM.household.service.HouseholdService;
import com.example.BTL_CNPM.resident.service.ResidentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HouseholdServiceImpl implements HouseholdService {

    @Autowired
    private HouseholdRepository householdRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ResidentService residentService;

//    @Autowired
//    private FeeManageService feeManageService;
//
//    @Autowired
//    private CharityService charityService;

    public boolean createHousehold(Household household) {
        if (!userRepository.existsByUsername(household.getOwnerUsername())) {
            return false;
        }

        if (householdRepository.existsByOwnerUsername(household.getOwnerUsername())) {
            return false;
        }

        householdRepository.save(household);

        return true;
    }

    @Override
    public boolean updateHousehold(Household household) {
        Optional<Household> existingHousehold = householdRepository.findByOwnerUsername(household.getOwnerUsername());

        if (existingHousehold.isPresent()) {
            Household updatedHousehold = existingHousehold.get();
            updatedHousehold.setNumOfMembers(household.getNumOfMembers());

            householdRepository.save(updatedHousehold);
            return true; // Cập nhật thành công
        }

        return false; // Không tìm thấy household để cập nhật
    }

    @Transactional
    @Override
    public boolean deleteHouseholdByUsername(String username) {
        Optional<Household> household = householdRepository.findByOwnerUsername(username);
        if (household.isPresent()) {
            Integer householdId = household.get().getId();
            // Xóa tất cả Residents trước
            residentService.deleteAllResidentByHouseholdId(householdId);

            // Xóa Household
            householdRepository.deleteByOwnerUsername(household.get().getOwnerUsername());
            return true;
        }
        return false;
    }

    @Override
    public List<Household> getAllHouseholds() {
        return householdRepository.findAll();
    }

    @Override
    public Household getHouseholdByUsername(String ownerUsername) {
        return householdRepository.findByOwnerUsername(ownerUsername)
                .orElse(null); // Trả về null nếu không tìm thấy
    }
}
