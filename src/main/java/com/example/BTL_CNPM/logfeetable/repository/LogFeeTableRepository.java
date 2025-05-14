package com.example.BTL_CNPM.logfeetable.repository;

import com.example.BTL_CNPM.logfeetable.model.LogFeeTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LogFeeTableRepository extends JpaRepository<LogFeeTable,Integer> {
    boolean existsById(Integer id);
    boolean existsByOwnerUserName(String ownerUserName);
    Optional<LogFeeTable> findById(Integer id);
    Optional<LogFeeTable> findByOwnerUserName(String ownerUserName);
    void deleteById(Integer id);
    void deleteByOwnerUserName(String ownerUserName);
    void deleteAll();
}
