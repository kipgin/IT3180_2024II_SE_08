package com.example.BTL_CNPM.logfeesection;


import com.example.BTL_CNPM.feemanage.model.FeeSection;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LogFeeSectionRepository extends JpaRepository<LogFeeSection,Integer>{
    List<LogFeeSection> findAll();
    Optional<LogFeeSection> findById(Integer id);
    void deleteById(Integer id);
    void deleteAll();
}
