package com.example.BTL_CNPM.duetimefee.repository;

import com.example.BTL_CNPM.duetimefee.model.DueTimeFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DueTimeFeeRepository extends JpaRepository<DueTimeFee,Integer> {
    boolean existsById(Integer id);
    boolean existsByName(String name);
    Optional<DueTimeFee> findById(Integer id);
    Optional<DueTimeFee> findByName(String name);

    List<DueTimeFee> findAll();
    void deleteById(Integer id);
    void deleteByName(String name);
    void deleteAll();
}
