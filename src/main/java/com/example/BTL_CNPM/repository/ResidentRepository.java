package com.example.BTL_CNPM.repository;

import com.example.BTL_CNPM.model.AccomStatus;
import com.example.BTL_CNPM.model.Resident;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResidentRepository extends JpaRepository<Resident, Long> {
    List<Resident> findByHouseholdId(Long householdId);
    List<Resident> findByAccomStatus(AccomStatus status);
}