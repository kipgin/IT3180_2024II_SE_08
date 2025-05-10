package com.example.BTL_CNPM.feemanage.repository;
import com.example.BTL_CNPM.feemanage.model.FeeName;



import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FeeNameRepository extends JpaRepository<FeeName,Integer>{
    boolean existsById(Integer id);
    boolean existsByName(String name);
    Optional<FeeName> findById(Integer id);
    Optional<FeeName> findByName(String name);
    List<FeeName> findAll();
    void deleteById(Integer id);
    void deleteByName(String name);
    void deleteAll();
}
