package com.example.BTL_CNPM.repository;


import com.example.BTL_CNPM.model.Charity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CharityRepository extends JpaRepository<Charity,Integer> {

    void deleteById(Integer id);

    void deleteAll();

    void deleteByOwnerUserName(String ownerUserName);

    boolean existsById(Integer id);
    boolean existsByOwnerUserName(String ownerUserName);
    Optional<Charity> findById(Integer id);
    Optional<Charity> findByOwnerUserName(String ownerUserName);
    List<Charity> findAll();
}
