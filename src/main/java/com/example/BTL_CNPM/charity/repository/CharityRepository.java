package com.example.BTL_CNPM.charity.repository;


import com.example.BTL_CNPM.charity.model.Charity;
import com.example.BTL_CNPM.charity.model.CharityName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharityRepository extends JpaRepository<Charity,Integer> {
    boolean existsById(Integer id);
    boolean existsByOwnerUserName(String ownerUserName);
    Optional<Charity> findById(Integer id);
    Optional<Charity> findByOwnerUserName(String ownerUserName);
    List<Charity> findAll();
    void deleteById(Integer id);
    void deleteByOwnerUserName(String ownerUserName);
    void deleteAll();
}
