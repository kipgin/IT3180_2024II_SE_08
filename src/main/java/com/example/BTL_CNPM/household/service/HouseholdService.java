package com.example.BTL_CNPM.household.service;

import com.example.BTL_CNPM.household.model.Household;

import java.util.List;

public interface HouseholdService {
    boolean createHousehold(Household household);
    boolean updateHousehold(Household household);
    boolean deleteHouseholdByUsername(String username);
    List<Household> getAllHouseholds();
    Household getHouseholdByUsername(String ownerUsername);
}