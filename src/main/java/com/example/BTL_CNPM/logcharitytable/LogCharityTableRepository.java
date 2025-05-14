package com.example.BTL_CNPM.logcharitytable;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogCharityTableRepository extends JpaRepository<LogCharityTable,Integer> {
    boolean existsById(Integer id);
    boolean existsByOwnerUserName(String ownerUserName);
    Optional<LogCharityTable> findById(Integer id);
    Optional<LogCharityTable> findByOwnerUserName(String ownerUserName);
    List<LogCharityTable> findAll();
    void deleteById(Integer id);
    void deleteByOwnerUserName(String ownerUserName);
    void deleteAll();
}
