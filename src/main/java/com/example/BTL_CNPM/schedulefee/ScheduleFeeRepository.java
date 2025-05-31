package com.example.BTL_CNPM.schedulefee;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ScheduleFeeRepository  extends JpaRepository<ScheduleFee,Integer> {
    Optional<ScheduleFee> findByName(String name);
    List<ScheduleFee> findAll();
    void deleteByName(String name);
}
