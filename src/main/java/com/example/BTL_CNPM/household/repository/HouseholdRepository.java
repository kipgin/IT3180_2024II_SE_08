package com.example.BTL_CNPM.household.repository;

import com.example.BTL_CNPM.household.model.Household;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HouseholdRepository extends JpaRepository<Household, Integer> {
    boolean existsByOwnerUsername(String ownerUsername);
    boolean existsById(Integer id);
    Optional<Household> findByOwnerUsername(String ownerUsername);
    void deleteByOwnerUsername(String ownerUsername);

}