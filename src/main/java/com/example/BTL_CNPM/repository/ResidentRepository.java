package com.example.BTL_CNPM.repository;

import com.example.BTL_CNPM.model.AccomStatus;
import com.example.BTL_CNPM.model.Resident;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Integer> {
    List<Resident> findByHouseholdId(Integer householdId);
    List<Resident> findByAccomStatus(AccomStatus status);
    void deleteAllByHouseholdId(Integer householdId);
    boolean existsByHouseholdId(Integer householdId);
}