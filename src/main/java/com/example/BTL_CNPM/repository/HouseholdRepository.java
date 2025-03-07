package com.example.BTL_CNPM.repository;

import com.example.BTL_CNPM.model.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Integer> {
    boolean existsByOwnerUsername(String ownerUsername);
    Optional<Household> findByOwnerUsername(String ownerUsername);
}