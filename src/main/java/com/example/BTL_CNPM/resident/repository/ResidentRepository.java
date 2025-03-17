package com.example.BTL_CNPM.resident.repository;

import com.example.BTL_CNPM.resident.model.AccomStatus;
import com.example.BTL_CNPM.resident.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Integer> {
    List<Resident> findByHouseholdId(Integer householdId);
    List<Resident> findByAccomStatus(AccomStatus status);
    void deleteAllByHouseholdId(Integer householdId);
    boolean existsByHouseholdId(Integer householdId);
}