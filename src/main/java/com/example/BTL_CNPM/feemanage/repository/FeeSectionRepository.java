package com.example.BTL_CNPM.feemanage.repository;

import com.example.BTL_CNPM.feemanage.model.FeeName;
import com.example.BTL_CNPM.feemanage.model.FeeSection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeSectionRepository extends JpaRepository<FeeSection,Integer> {
    boolean existsById(Integer id);
    Optional<FeeSection> findById(Integer id);
    List<FeeSection> findAll();
    List<FeeSection> findByName(String name);
    void deleteById(Integer id);
    void deleteAll();
}
