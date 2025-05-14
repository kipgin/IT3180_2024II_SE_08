package com.example.BTL_CNPM.logcharitysection;


import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogCharitySectionRepository extends JpaRepository<LogCharitySection,Integer> {
    boolean existsById(Integer id);
    Optional<LogCharitySection> findById(Integer id);
    List<LogCharitySection> findAll();
    void deleteById(Integer id);
    void deleteAll();
}
